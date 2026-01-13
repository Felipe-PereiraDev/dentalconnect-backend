package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PutMapping(value = "/{patientId}/addresses/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable("patientId") UUID patientId,
                                              @PathVariable("addressId") Long addressId,
                                              @RequestBody AddressUpdateDTO data) {
        AddressResponseDTO addressResponseDTO = patientService.updateAddress(patientId, addressId, data);
        return ResponseEntity.ok(addressResponseDTO);
    }
}
