package com.github.felipe_pereiradev.dentalconnect.dto.dentist;

import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyResponseDTO;

import java.util.List;
import java.util.UUID;

public record DentistResponseDTO(
        UUID id,
        String email,
        String name,
        String phone,
        String licenseNumber,
        UUID clinicId,
        List<SpecialtyResponseDTO> specialties
) {
}
