package com.github.felipe_pereiradev.dentalconnect.dto.procedure;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProcedureRequestDTO(
        @NotBlank
        @Size(min = 5, max = 200)
        String name,
        @NotBlank
        @Size(min = 5, max = 2000)
        String description
) {
}
