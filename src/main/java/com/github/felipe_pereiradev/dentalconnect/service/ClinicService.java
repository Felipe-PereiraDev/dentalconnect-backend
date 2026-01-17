package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.config.security.UserContextService;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.appointment.AppointmentRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.appointment.AppointmentResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.ForbiddenException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.mapper.AddressMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.model.Patient;
import com.github.felipe_pereiradev.dentalconnect.repository.ClinicRepository;
import com.github.felipe_pereiradev.dentalconnect.utils.GeoDistanceUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final UserContextService userContextService;
    private final AppointmentService appointmentService;

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
                        c.getId(),
                        c.getName(),
                        c.getPhone(),
                        c.getCnpj(),
                        addressMapper.toResponse(c.getAddress()),
                        GeoDistanceUtils.calculateRadiusKm(
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
}
