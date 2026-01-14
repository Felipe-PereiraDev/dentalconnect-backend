package com.github.felipe_pereiradev.dentalconnect.dto.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentRequestDTO(
        LocalDate date,
        LocalTime hour,
        UUID procedureId,
        Double radiusKm
) {
}
