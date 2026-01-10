package com.github.felipe_pereiradev.dentalconnect.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleResponseDTO(
        UUID id,
        UUID dentistId,
        UUID clinicId,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateAt,
        @JsonFormat(pattern = "HH:ss")
        LocalTime startsAt,
        @JsonFormat(pattern = "HH:ss")
        LocalTime endsAt
) {
}
