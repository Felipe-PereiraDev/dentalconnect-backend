package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.config.security.UserContextService;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.ForbiddenException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.mapper.AddressMapper;
import com.github.felipe_pereiradev.dentalconnect.mapper.ClinicMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.model.Patient;
import com.github.felipe_pereiradev.dentalconnect.repository.ClinicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final UserContextService userContextService;

    public Clinic createClinic(ClinicRequestDTO data, Address address) {
        Clinic clinic = new Clinic(
                address,
                data.cnpj(),
                data.name(),
                data.phone()
        );
        return clinicRepository.save(clinic);
    }

    public List<ClinicResponseDTO> searchClinicsForPatient(Long specialtyId, LocalDate date, double radiusKm) {
        Patient authenticatedPatient = userContextService.getAuthenticatedPatient();
        Address patientAddress = authenticatedPatient.getAddress();
        List<Clinic> clinicList = clinicRepository.searchClinicsForPatient(
                specialtyId,
                date,
                patientAddress.getLatitude(),
                patientAddress.getLongitude(),
                radiusKm
        );
        return clinicList.stream()
                .map(c -> new ClinicResponseDTO(
                        c.getName(),
                        c.getPhone(),
                        c.getCnpj(),
                        addressMapper.toResponse(c.getAddress()),
                        calculateRadiusKm(
                                c.getAddress().getLatitude(),
                                c.getAddress().getLongitude(),
                                patientAddress.getLatitude(),
                                patientAddress.getLongitude()
                        )
                )).toList();
    }

    public Clinic findById(UUID id) {
        return clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("clinic Not found"));
    }

    @Transactional
    public AddressResponseDTO updateAddress(UUID clinicId, Long addressId, AddressUpdateDTO data) {
        Clinic clinic = findById(clinicId);
        Address address = addressService.findById(addressId);

        if (!clinic.getAddress().equals(address)) {
            throw new ForbiddenException("you don't have permission to update this address.");
        }
        return addressService.update(address, data);
    }

    private double calculateRadiusKm(double clinicLat, double clinicLon, double userLat, double userLon) {
        final double EARTH_RADIUS_KM = 6371.0;

        double latDistance = Math.toRadians(userLat - clinicLat);
        double lonDistance = Math.toRadians(userLon - clinicLon);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(clinicLat))
                * Math.cos(Math.toRadians(userLat))
                * Math.sin(lonDistance / 2)
                * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_KM * c;
        return  BigDecimal.valueOf(distance)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
