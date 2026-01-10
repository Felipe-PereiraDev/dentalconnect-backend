package com.github.felipe_pereiradev.dentalconnect.dto.address;

public record AddressUpdateDTO(
        String zipCode,
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city
) {
}
