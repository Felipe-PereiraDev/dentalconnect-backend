package com.github.felipe_pereiradev.dentalconnect.dto.clinic;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClinicRequestDTO(
        @NotBlank @Size(min = 5, max = 100)
        String name,
        @NotBlank @Size(min = 4, max = 20)
        String phone,
        @NotBlank @Size(min = 14, max = 14)
        String cnpj,
        @NotNull
        AddressRequestDTO address
) {
}
