package com.github.felipe_pereiradev.dentalconnect.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleTime {
    private UUID id;
    @JsonFormat(pattern = "HH:ss")
    private LocalTime startsAt;
    @JsonFormat(pattern = "HH:ss")
    private LocalTime endsAt;
}
