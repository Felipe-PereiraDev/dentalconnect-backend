package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyIDRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.ConflictException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.exception.UnprocessableEntityException;
import com.github.felipe_pereiradev.dentalconnect.mapper.SpecialtyMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Specialty;
import com.github.felipe_pereiradev.dentalconnect.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyResponseDTO create(SpecialtyRequestDTO data) {
        if (specialtyRepository.existsByName(data.name())) {
            throw new ConflictException("specialty already exists");
        }
        Specialty specialty = new Specialty(data.name(), data.description());
        specialtyRepository.save(specialty);
        return specialtyMapper.toResponseDTO(specialty);
    }

    public Page<SpecialtyResponseDTO> findAll(Pageable pageable) {
        return specialtyRepository
                .findAll(pageable)
                .map(specialtyMapper::toResponseDTO);
    }

    public List<Specialty> findAllById(List<SpecialtyIDRequestDTO> specialtyIDRequestDTOS) {
        List<Long> ids = specialtyIDRequestDTOS.stream()
                .map(SpecialtyIDRequestDTO::id)
                .toList();
        List<Specialty> patients = specialtyRepository.findAllById(ids);
        if (patients.isEmpty()) {
            throw new ResourceNotFoundException("specialty not found");
        }
        return patients;
    }

    public SpecialtyResponseDTO findById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("specialty not found")
                );
        return specialtyMapper.toResponseDTO(specialty);
    }

    public void deleteById(Long id){
        if (!specialtyRepository.existsById(id)) {
            throw new ResourceNotFoundException("specialty not found");
        }

        try {
            specialtyRepository.deleteById(id);
            specialtyRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new UnprocessableEntityException("specialty cannot be deleted because it is referenced by another resource");
        }
    }
}
