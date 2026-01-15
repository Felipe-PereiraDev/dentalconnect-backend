package com.github.felipe_pereiradev.dentalconnect.dto.dentist;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DentistIdDTO(
        @NotNull
        UUID id
) {
}
