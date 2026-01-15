package com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ClinicProcedureUpdateDTO(
        @Positive
        BigDecimal price,
        @Positive
        Long duration
) {
}
