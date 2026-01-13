package com.github.felipe_pereiradev.dentalconnect.model;


import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.integration.geocoding.GeocodingResult;
import com.github.felipe_pereiradev.dentalconnect.dto.integration.viacep.ViaCepResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    public void update(AddressUpdateDTO addressDTO, ViaCepResponse viaCepResponse, GeocodingResult geocodingResult) {
        if (addressDTO.number() != null && !addressDTO.number().isBlank()) {
            this.number = addressDTO.number();
        }
        if (addressDTO.complement() != null && !addressDTO.complement().isBlank()) {
            this.complement = addressDTO.complement();
        }
        if (viaCepResponse.cep() != null && !viaCepResponse.cep().isBlank()) {
            this.zipCode = viaCepResponse.cep();
        }
        if (viaCepResponse.logradouro() != null && !viaCepResponse.logradouro().isBlank()) {
            this.street = viaCepResponse.logradouro();
        }
        if (viaCepResponse.bairro() != null && !viaCepResponse.bairro().isBlank()) {
            this.neighborhood = viaCepResponse.bairro();
        }
        if (viaCepResponse.localidade() != null && !viaCepResponse.localidade().isBlank()) {
            this.city = viaCepResponse.localidade();
        }
        if (viaCepResponse.estado() != null && !viaCepResponse.estado().isBlank()) {
            this.state = viaCepResponse.estado();
        }

        if (geocodingResult.getLatitude() != 0.0) {
            this.latitude = geocodingResult.getLatitude();
        }
        if (geocodingResult.getLongitude() != 0.0) {
            this.longitude = geocodingResult.getLongitude();
        }
    }
}
