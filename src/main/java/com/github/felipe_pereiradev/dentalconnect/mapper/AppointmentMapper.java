package com.github.felipe_pereiradev.dentalconnect.mapper;


import com.github.felipe_pereiradev.dentalconnect.dto.appointment.AppointmentResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "appointment.id", target = "id")
    @Mapping(source = "appointment.clinic.id", target = "clinicId")
    @Mapping(source = "appointment.patient.id", target = "patientId")
    @Mapping(source = "appointment.dentist.id", target = "dentistId")
    @Mapping(source = "appointment.clinicProcedure.id", target = "procedureId")
    AppointmentResponseDTO toResponseDTO(Appointment appointment);

}
