package com.github.felipe_pereiradev.dentalconnect.dto.schedule;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleRequestDTO(
        @NotNull
        UUID clinicId,
        @NotNull
        UUID dentistId,
        @NotNull
        LocalDate dateAt,
        @NotNull
        LocalTime startsAt,
        @NotNull
        LocalTime endsAt

) {
}
