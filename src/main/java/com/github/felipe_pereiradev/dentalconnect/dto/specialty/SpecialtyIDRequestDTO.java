package com.github.felipe_pereiradev.dentalconnect.dto.specialty;

import jakarta.validation.constraints.NotNull;

public record SpecialtyIDRequestDTO(
        @NotNull
        Long id
) {
}
