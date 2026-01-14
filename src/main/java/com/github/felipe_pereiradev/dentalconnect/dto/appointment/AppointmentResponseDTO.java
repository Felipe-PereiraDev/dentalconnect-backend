package com.github.felipe_pereiradev.dentalconnect.dto.appointment;

import com.github.felipe_pereiradev.dentalconnect.enums.AppointmentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentResponseDTO(
        UUID id,
        UUID patientId,
        UUID clinicId,
        UUID dentistId,
        AppointmentStatusEnum status,
        BigDecimal price,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        UUID procedureId
) {
}
