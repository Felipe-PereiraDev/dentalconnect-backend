package com.github.felipe_pereiradev.dentalconnect.dto.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleUpdateDTO(
        LocalDate dateAt,
        LocalTime startsAt,
        LocalTime endsAt
) {
}
