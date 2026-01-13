package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.ForbiddenException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.mapper.ClinicMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.repository.ClinicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;
    private AddressService addressService;

    public Clinic createClinic(ClinicRequestDTO data, Address address) {
        Clinic clinic = new Clinic(
                address,
                data.cnpj(),
                data.name(),
                data.phone()
        );
        return clinicRepository.save(clinic);
    }

    public List<Clinic> searchClinicsForPatient(Long specialtyId, LocalDate date, String state) {
        return clinicRepository.searchClinicsForPatient(specialtyId, date, state);
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
