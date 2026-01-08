package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRegistrationRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.employee.EmployeeRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/signup")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(value = "/clinic")
    public ResponseEntity<?> registerClinicWithOwner(@RequestBody @Validated ClinicRegistrationRequestDTO data) {
        registrationService.registerClinicWithOwner(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody @Validated EmployeeRequestDTO data) {
        registrationService.registerEmployee(data);
        return ResponseEntity.ok().build();
    }
}
