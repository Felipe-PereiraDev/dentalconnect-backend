package com.github.felipe_pereiradev.dentalconnect.dto.dentist;

import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyIDRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.model.Specialty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record DentistRequestDTO(
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 6, max = 100)
        String password,
        @NotBlank @Size(min = 6, max = 100)
        String name,
        @NotBlank @Size(min = 4, max = 20)
        String phone,
        @NotBlank
        String licenseNumber,
        @NotNull
        UUID clinicId,
        @Size(min = 1, message = "adicione pelo menos uma especialidade")
        List<SpecialtyIDRequestDTO> specialties
) {
}
