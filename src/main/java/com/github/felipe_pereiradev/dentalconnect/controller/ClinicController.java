package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping(value = "/search")
    public ResponseEntity<List<ClinicResponseDTO>> searchClinicsForPatient(@RequestParam("specialtyId") Long specialtyId,
                                                                           @RequestParam("date") LocalDate date,
                                                                           @RequestParam("radiusKm") double radiusKm) {
        List<ClinicResponseDTO> clinics = clinicService.searchClinicsForPatient(specialtyId, date, radiusKm);
        return ResponseEntity.ok(clinics);
    }

    @PutMapping(value = "/{clinicId}/addresses/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable("clinicId") UUID patientId,
                                              @PathVariable("addressId") Long addressId,
                                              @RequestBody AddressUpdateDTO data) {
        AddressResponseDTO addressResponseDTO = clinicService.updateAddress(patientId, addressId, data);
        return ResponseEntity.ok(addressResponseDTO);
    }
}
