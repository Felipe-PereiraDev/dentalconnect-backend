package com.github.felipe_pereiradev.dentalconnect.dto.specialty;

import jakarta.validation.constraints.NotBlank;

public record SpecialtyRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String description
) {
}
