package com.github.felipe_pereiradev.dentalconnect.dto.clinic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClinicRegistrationRequestDTO(
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 6, max = 100)
        String password,
        @NotBlank @Size(min = 6, max = 100)
        String name,
        @NotBlank @Size(min = 4, max = 20)
        String phone,
        @NotNull
        ClinicRequestDTO clinic
) {
}
