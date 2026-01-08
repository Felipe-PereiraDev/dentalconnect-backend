package com.github.felipe_pereiradev.dentalconnect.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EmployeeRequestDTO(
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 6, max = 100)
        String password,
        @NotBlank @Size(min = 6, max = 100)
        String name,
        @NotBlank @Size(min = 4, max = 20)
        String phone,
        @NotBlank
        String jobTitle,
        @NotNull
        UUID clinicId
) {
}
