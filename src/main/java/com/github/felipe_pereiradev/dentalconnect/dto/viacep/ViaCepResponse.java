package com.github.felipe_pereiradev.dentalconnect.dto.viacep;

public record ViaCepResponse(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String estado
) {
}
