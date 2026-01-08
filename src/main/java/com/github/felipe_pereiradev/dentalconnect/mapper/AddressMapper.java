package com.github.felipe_pereiradev.dentalconnect.mapper;


import com.github.felipe_pereiradev.dentalconnect.dto.viacep.ViaCepResponse;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(source = "viaCepResponse.cep", target = "zipCode")
    @Mapping(source = "viaCepResponse.logradouro", target = "street")
    @Mapping(source = "viaCepResponse.bairro", target = "neighborhood")
    @Mapping(source = "viaCepResponse.estado", target = "state")
    @Mapping(source = "viaCepResponse.localidade", target = "city")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "complement", target = "complement")
    Address toEntity(ViaCepResponse viaCepResponse, String number, String complement);
}
