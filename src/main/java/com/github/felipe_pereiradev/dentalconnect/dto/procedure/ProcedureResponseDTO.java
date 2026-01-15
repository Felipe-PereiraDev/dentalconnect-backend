package com.github.felipe_pereiradev.dentalconnect.dto.procedure;

import java.util.UUID;

public record ProcedureResponseDTO(
        UUID id,
        String name,
        String description
) {
}
