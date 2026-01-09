package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.model.*;
import com.github.felipe_pereiradev.dentalconnect.repository.DentistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentistService {

    private final DentistRepository dentistRepository;

    public Dentist create(String name, String phone, String licenseNumber, Clinic clinic, User user, List<Specialty> specialtyList) {
        if (dentistRepository.existsByLicenseNumber(licenseNumber)) {
            throw new ResourceNotFoundException("licenseNumber unavailable");
        }

        if (dentistRepository.existsByPhone(phone)) {
            throw new ResourceNotFoundException("phone unavailable");
        }

        Dentist dentist = new Dentist(
                name,
                phone,
                PersonType.DENTIST,
                user,
                clinic,
                licenseNumber,
                specialtyList
        );
        return dentistRepository.save(dentist);
    }


}
