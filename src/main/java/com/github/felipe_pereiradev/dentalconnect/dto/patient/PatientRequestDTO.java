package com.github.felipe_pereiradev.dentalconnect.dto.patient;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record PatientRequestDTO(
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 6, max = 100)
        String password,
        @NotBlank @Size(min = 6, max = 100)
        String name,
        @NotBlank @Size(min = 4, max = 20)
        String phone,
        @NotBlank @Size(min = 11, max = 11) @CPF
        String cpf,
        @NotNull
        AddressRequestDTO address
) {
}
