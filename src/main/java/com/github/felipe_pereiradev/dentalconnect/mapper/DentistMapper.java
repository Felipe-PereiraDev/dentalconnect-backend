package com.github.felipe_pereiradev.dentalconnect.mapper;

import com.github.felipe_pereiradev.dentalconnect.dto.dentist.DentistResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.model.Dentist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        SpecialtyMapper.class
})
public interface DentistMapper {

    @Mapping(source = "clinic.id", target = "clinicId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "specialtyList", target = "specialties")
    DentistResponseDTO toResponseDTO(Dentist dentist);
}
