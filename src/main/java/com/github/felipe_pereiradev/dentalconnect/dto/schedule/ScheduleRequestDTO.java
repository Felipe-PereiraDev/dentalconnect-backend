package com.github.felipe_pereiradev.dentalconnect.dto.schedule;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleRequestDTO(
        @NotNull
        LocalDate dateAt,
        @NotNull
        LocalTime startsAt,
        @NotNull
        LocalTime endsAt
) {
}
