package com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure;

import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequestDTO(
        @NotNull
        boolean active
) {
}
