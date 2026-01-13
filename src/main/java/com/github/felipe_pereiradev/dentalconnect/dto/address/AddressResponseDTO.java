package com.github.felipe_pereiradev.dentalconnect.dto.address;

public record AddressResponseDTO(
        Long id,
        String zipCode,
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city
) {
}
