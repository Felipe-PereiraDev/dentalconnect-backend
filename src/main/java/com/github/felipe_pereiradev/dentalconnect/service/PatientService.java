package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import com.github.felipe_pereiradev.dentalconnect.exception.ConflictException;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.model.Patient;
import com.github.felipe_pereiradev.dentalconnect.model.User;
import com.github.felipe_pereiradev.dentalconnect.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

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


}
