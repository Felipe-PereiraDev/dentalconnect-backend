package com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure;

import com.github.felipe_pereiradev.dentalconnect.dto.dentist.DentistIdDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ClinicProcedureRequestDTO(
        @NotNull @Positive
        BigDecimal price,
        @NotNull @Positive
        Long duration,
        @NotNull
        UUID clinicId,
        @NotNull
        UUID procedureId,
        @NotNull @NotEmpty @Valid
        List<DentistIdDTO> dentists
) {
}
