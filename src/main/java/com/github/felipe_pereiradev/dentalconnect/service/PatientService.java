package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import com.github.felipe_pereiradev.dentalconnect.exception.ConflictException;
import com.github.felipe_pereiradev.dentalconnect.exception.ForbiddenException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.model.Patient;
import com.github.felipe_pereiradev.dentalconnect.model.User;
import com.github.felipe_pereiradev.dentalconnect.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final AddressService addressService;

    public Patient create(String name, String phone, String cpf, Address address, User user) {
        if (patientRepository.existsByCpf(cpf)) {
            throw new ConflictException("CPF unavailable");
        }

        Patient patient = new Patient(
                name,
                phone,
                PersonType.PATIENT,
                user,
                address,
                cpf
        );
        return patientRepository.save(patient);
    }

    @Transactional
    public AddressResponseDTO updateAddress(UUID patientId, Long addressId, AddressUpdateDTO data) {
        Patient patient = findById(patientId);
        Address address = addressService.findById(addressId);

        if (!patient.getAddress().equals(address)) {
            throw new ForbiddenException("you don't have permission to update this address.");
        }
        return addressService.update(address, data);
    }

    public Patient findById(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("patient not found")
                );
    }
}
