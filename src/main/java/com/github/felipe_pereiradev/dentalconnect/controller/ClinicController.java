package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PutMapping(value = "/{clinicId}/addresses/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable("clinicId") UUID patientId,
                                              @PathVariable("addressId") Long addressId,
                                              @RequestBody AddressUpdateDTO data) {
        AddressResponseDTO addressResponseDTO = clinicService.updateAddress(patientId, addressId, data);
        return ResponseEntity.ok(addressResponseDTO);
    }
}
