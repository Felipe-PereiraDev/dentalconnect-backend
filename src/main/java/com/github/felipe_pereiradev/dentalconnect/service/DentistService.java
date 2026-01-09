package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.dentist.DentistResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import com.github.felipe_pereiradev.dentalconnect.exception.ConflictException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.mapper.DentistMapper;
import com.github.felipe_pereiradev.dentalconnect.model.*;
import com.github.felipe_pereiradev.dentalconnect.repository.DentistRepository;
import com.github.felipe_pereiradev.dentalconnect.repository.SpecialtyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DentistService {

    private final DentistRepository dentistRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DentistMapper dentistMapper;

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

    @Transactional
    public DentistResponseDTO addSpecialtyToDentist(UUID dentistId, Long specialtyId) {
        Dentist dentist = dentistRepository.findById(dentistId)
                .orElseThrow(() -> new ResourceNotFoundException("dentist not found"));

        if (!specialtyRepository.existsById(specialtyId)) {
            throw new ResourceNotFoundException("specialty not found");
        }
        Specialty specialty = specialtyRepository.getReferenceById(specialtyId);

        dentist.addSpecialty(specialty);
        dentistRepository.save(dentist);
        return dentistMapper.toResponseDTO(dentist);
    }

    @Transactional
    public DentistResponseDTO removeSpecialtyFromDentist(UUID dentistId, Long specialtyId) {
        Dentist dentist = dentistRepository.findById(dentistId)
                .orElseThrow(() -> new ResourceNotFoundException("dentist not found"));

        if (!specialtyRepository.existsById(specialtyId)) {
            throw new ResourceNotFoundException("specialty not found");
        }
        Specialty specialty = specialtyRepository.getReferenceById(specialtyId);

        dentist.removeSpecialty(specialty);
        dentistRepository.save(dentist);
        return dentistMapper.toResponseDTO(dentist);
    }


}
