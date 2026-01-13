package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.integration.geocoding.GeocodingResponse;
import com.github.felipe_pereiradev.dentalconnect.dto.integration.geocoding.GeocodingResult;
import com.github.felipe_pereiradev.dentalconnect.dto.integration.viacep.ViaCepResponse;
import com.github.felipe_pereiradev.dentalconnect.exception.BadRequestException;
import com.github.felipe_pereiradev.dentalconnect.exception.ExternalServiceException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.integration.GoogleGeocodingClient;
import com.github.felipe_pereiradev.dentalconnect.integration.ViaCepClient;
import com.github.felipe_pereiradev.dentalconnect.mapper.AddressMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.repository.AddressRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ViaCepClient viaCepClient;
    private final GoogleGeocodingClient googleGeocodingClient;


    @Transactional
    public Address create(AddressRequestDTO data) {
        ViaCepResponse viaCepResponse = getAddressByZipCode(data.zipCode());
        GeocodingResult geocodingResult = getGeocodingByZipCode(data.zipCode());
        double latitude = geocodingResult.getLatitude();
        double longitude = geocodingResult.getLongitude();
        Address address = addressMapper.toEntity(viaCepResponse, data.number(), data.complement(), latitude, longitude);
        return addressRepository.save(address);
    }


    public AddressResponseDTO update(Address address, AddressUpdateDTO dto) {
        ViaCepResponse viaCepResponse = getAddressByZipCode(dto.zipCode());
        GeocodingResult geocodingResult = getGeocodingByZipCode(dto.zipCode());
        address.update(dto, viaCepResponse, geocodingResult);
        addressRepository.save(address);
        return addressMapper.toResponse(address);
    }

    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("address not found")
                );
    }

    private ViaCepResponse getAddressByZipCode(String zipCode) {
        String cleanZipCode  = zipCode.replaceAll("\\s+", "");
        try {
            return viaCepClient.getAddressByZipCode(cleanZipCode);
        } catch (FeignException.FeignClientException ex) {
            throw new BadRequestException("CEP NÃO ENCONTRADO");
        }
    }

    private GeocodingResult getGeocodingByZipCode(String zipCode) {
        String cleanZipCode  = zipCode.replaceAll("\\s+", "");
        try {
            GeocodingResponse response = googleGeocodingClient.getGeocodingByZipCode(cleanZipCode);

            return response.results()
                    .stream()
                    .findFirst()
                    .orElseThrow(
                            () ->  new ResourceNotFoundException("location not found for CEP: " + cleanZipCode)
                    );
        } catch (FeignException.BadRequest ex) {
            throw new BadRequestException("invalid zip code");
        } catch (FeignException ex) {
            throw new ExternalServiceException("failed to retrieve geolocation data");
        }
    }
}
