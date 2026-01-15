package com.github.felipe_pereiradev.dentalconnect.mapper;


import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.model.ClinicProcedure;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClinicProcedureMapper {

    @Mapping(source = "clinicProcedure.id", target = "id")
    @Mapping(source = "clinicProcedure.clinic.id", target = "clinicId")
    @Mapping(source = "clinicProcedure.procedure.id", target = "procedureId")
    ClinicProcedureResponseDTO toResponseDTO(ClinicProcedure clinicProcedure);

    List<ClinicProcedureResponseDTO> toResponseDTOList(List<ClinicProcedure> clinicProcedureList);
}
