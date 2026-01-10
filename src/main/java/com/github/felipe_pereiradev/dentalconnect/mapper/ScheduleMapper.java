package com.github.felipe_pereiradev.dentalconnect.mapper;


import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "schedule.clinic.id", target = "clinicId")
    @Mapping(source = "schedule.dentist.id", target = "dentistId")
    ScheduleResponseDTO toResponseDTO(Schedule schedule);
}
