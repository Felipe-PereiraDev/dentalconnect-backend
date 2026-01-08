package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.address.AddressRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.viacep.ViaCepResponse;
import com.github.felipe_pereiradev.dentalconnect.exception.BadRequestException;
import com.github.felipe_pereiradev.dentalconnect.integration.ViaCepClient;
import com.github.felipe_pereiradev.dentalconnect.mapper.AddressMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.repository.AddressRepository;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ViaCepClient viaCepClient;

    public Address create(AddressRequestDTO data) {
        ViaCepResponse viaCepResponse = getAddressByZipCode(data.zipCode());
        Address address = addressMapper.toEntity(viaCepResponse, data.number(), data.complement());
        return addressRepository.save(address);
    }

    private ViaCepResponse getAddressByZipCode(String cep) {
        cep  = cep.replace("\\s+", "");
        try {
            return viaCepClient.getAddressByZipCode(cep);
        } catch (FeignException.FeignClientException ex) {
            throw new BadRequestException("CEP NÃO ENCONTRADO");
        }
    }
}
