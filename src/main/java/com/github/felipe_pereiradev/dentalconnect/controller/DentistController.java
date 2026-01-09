package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.dentist.DentistResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/dentists")
@RequiredArgsConstructor
public class DentistController {

    private final DentistService dentistService;

    @PostMapping(value = "{dentistId}/specialties/{specialtyId}")
    public ResponseEntity<?> addSpecialtyToDentist(@PathVariable("dentistId") UUID dentistId,
                                                   @PathVariable("specialtyId") Long specialtyId) {
        DentistResponseDTO dentistResponseDTO = dentistService.addSpecialtyToDentist(dentistId, specialtyId);
        return ResponseEntity.ok().body(dentistResponseDTO);
    }

    @DeleteMapping(value = "{dentistId}/specialties/{specialtyId}")
    public ResponseEntity<?> removeSpecialtyFromDentist(@PathVariable("dentistId") UUID dentistId,
                                                   @PathVariable("specialtyId") Long specialtyId) {
        DentistResponseDTO dentistResponseDTO = dentistService.removeSpecialtyFromDentist(dentistId, specialtyId);
        return ResponseEntity.ok().body(dentistResponseDTO);
    }
}
