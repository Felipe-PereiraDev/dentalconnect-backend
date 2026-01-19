package com.github.felipe_pereiradev.dentalconnect.controller.Docs;

import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleTime;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(
        name = "Schedules",
        description = "Gerenciamento de agendas e horários dos dentistas"
)
public interface ScheduleControllerDocs {

    @Operation(
            summary = "Criar agenda",
            description = "Cria uma nova agenda de horários para um dentista"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agenda criada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Conflito de horários")
    })
    ResponseEntity<ScheduleResponseDTO> createSchedule(@RequestBody @Validated ScheduleRequestDTO data);

    @Operation(
            summary = "Buscar agenda por dentista",
            description = "Retorna a agenda organizada por data de um dentista"
    )
    @ApiResponse(responseCode = "200", description = "Agenda retornada com sucesso")
    ResponseEntity<Map<LocalDate, List<ScheduleTime>>> getSchedulesByDentist(@PathVariable("dentistId") UUID dentistId);

    @Operation(
            summary = "Remover horário",
            description = "Remove um horário específico da agenda do dentista"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Horário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agenda ou horário não encontrado")
    })
    ResponseEntity<Void> deleteScheduleById(@PathVariable("dentistId") UUID dentistId, @PathVariable("scheduleId") UUID scheduleId);

    @Operation(
            summary = "Atualizar horário",
            description = "Atualiza um horário existente na agenda do dentista"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agenda ou horário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de horários")
    })
    ResponseEntity<ScheduleResponseDTO> updateSchedule(
            @PathVariable("dentistId") UUID dentistId,
            @PathVariable("scheduleId") UUID scheduleId,
            @RequestBody ScheduleUpdateDTO data);

}