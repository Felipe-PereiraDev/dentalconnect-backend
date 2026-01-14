package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.appointment.AppointmentRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.appointment.AppointmentResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;

    @PostMapping(value = "/clinics/{clinicId}/patients/{patientId}")
    public ResponseEntity<?> createAppointment(@PathVariable("patientId") UUID patientId,
                                               @PathVariable("clinicId") UUID clinicId,
                                               @RequestBody @Validated AppointmentRequestDTO data) {
        AppointmentResponseDTO appointmentResponse = appointmentService.create(clinicId, patientId, data);
        return ResponseEntity.ok(appointmentResponse);
    }
}
