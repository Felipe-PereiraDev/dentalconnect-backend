package com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure;

import java.math.BigDecimal;
import java.util.UUID;

public record ClinicProcedureResponseDTO(
        UUID id,
        BigDecimal price,
        Long durationInMinutes,
        UUID clinicId,
        UUID procedureId
) {
}
