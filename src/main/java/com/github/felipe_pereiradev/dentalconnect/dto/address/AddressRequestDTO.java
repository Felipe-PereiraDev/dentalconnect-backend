package com.github.felipe_pereiradev.dentalconnect.dto.address;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(
            @NotBlank
            String zipCode,
            @NotBlank
            String street,
            String number,
            String complement,
            String neighborhood,
            String state,
            String city
    ) {
}
