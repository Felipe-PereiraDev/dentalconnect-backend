package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.EntityNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.mapper.ClinicMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.repository.ClinicRepository;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public Clinic createClinic(ClinicRequestDTO data, Address address) {
        Clinic clinic = new Clinic(
                address,
                data.cnpj(),
                data.name(),
                data.phone()
        );
        return clinicRepository.save(clinic);
    }

    public Clinic findById(UUID id) {
        return clinicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clinic Not found"));
    }
}
