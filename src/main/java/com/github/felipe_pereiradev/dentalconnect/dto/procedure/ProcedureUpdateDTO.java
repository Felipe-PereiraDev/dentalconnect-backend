package com.github.felipe_pereiradev.dentalconnect.dto.procedure;

import jakarta.validation.constraints.Size;

public record ProcedureUpdateDTO(
        @Size(min = 5, max = 200)
        String name,
        @Size(min = 5, max = 2000)
        String description
) {
}
