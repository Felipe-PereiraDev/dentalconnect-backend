package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyIDRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.EntityNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.model.Specialty;
import com.github.felipe_pereiradev.dentalconnect.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public List<Specialty> findAllById(List<SpecialtyIDRequestDTO> specialtyIDRequestDTOS) {
        List<Long> ids = specialtyIDRequestDTOS.stream()
                .map(SpecialtyIDRequestDTO::id)
                .toList();
        List<Specialty> patients = specialtyRepository.findAllById(ids);
        if (patients.isEmpty()) {
            throw new EntityNotFoundException("specialty not found");
        }
        return patients;
    }
}
