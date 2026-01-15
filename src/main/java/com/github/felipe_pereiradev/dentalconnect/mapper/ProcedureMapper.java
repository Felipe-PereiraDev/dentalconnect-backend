package com.github.felipe_pereiradev.dentalconnect.mapper;

import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.model.Procedure;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcedureMapper {

    ProcedureResponseDTO toResponseDTO(Procedure procedure);
    List<ProcedureResponseDTO> toResponseDTOList(List<Procedure> procedure);
}
