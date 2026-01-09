package com.github.felipe_pereiradev.dentalconnect.mapper;

import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.model.Specialty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    SpecialtyResponseDTO toResponseDTO(Specialty specialty);

    List<SpecialtyResponseDTO> toResponseDTOList(List<Specialty> specialtyList);
}
