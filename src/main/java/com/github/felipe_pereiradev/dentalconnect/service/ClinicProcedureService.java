package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.model.ClinicProcedure;
import com.github.felipe_pereiradev.dentalconnect.repository.ClinicProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicProcedureService {
    private final ClinicProcedureRepository clinicProcedureRepository;

    public ClinicProcedure findByClinicIdAndProcedureId(UUID clinicId, UUID procedureId) {
        return clinicProcedureRepository.findByClinicIdAndProcedureId(clinicId, procedureId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("procedimento não encontrado")
                );

    }
}
