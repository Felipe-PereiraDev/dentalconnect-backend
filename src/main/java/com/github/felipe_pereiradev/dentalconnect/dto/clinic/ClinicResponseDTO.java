package com.github.felipe_pereiradev.dentalconnect.dto.clinic;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;

import java.util.UUID;

public record ClinicResponseDTO (
        UUID id,
        String name,
        String phone,
        String cnpj,
        AddressResponseDTO address,
        double radiusKm
) {
}
