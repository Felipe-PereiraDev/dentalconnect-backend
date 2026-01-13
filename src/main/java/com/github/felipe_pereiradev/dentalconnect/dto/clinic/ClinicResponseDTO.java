package com.github.felipe_pereiradev.dentalconnect.dto.clinic;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;

public record ClinicResponseDTO (
        String name,
        String phone,
        String cnpj,
        AddressResponseDTO address,
        double radiusKm
) {
}
