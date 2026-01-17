package com.github.felipe_pereiradev.dentalconnect.dto.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentRequestDTO(
        @NotNull
        UUID patientId,
        @NotNull
        UUID clinicId,
        @NotNull
        UUID procedureId,
        @NotNull
        LocalDate date,
        @NotNull
        LocalTime hour,
        @NotNull @Positive
        Double radiusKm
) {
}
