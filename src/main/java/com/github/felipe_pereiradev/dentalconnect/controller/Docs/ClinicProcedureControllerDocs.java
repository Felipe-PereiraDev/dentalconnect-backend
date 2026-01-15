package com.github.felipe_pereiradev.dentalconnect.controller.Docs;

import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ChangeStatusRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;


@Tag(
        name = "Clinic Procedures",
        description = "Gerenciamento dos procedimentos oferecidos pelas clínicas"
)
public interface ClinicProcedureControllerDocs {

    @Operation(
            summary = "Ofertar procedimento em uma clínica",
            description = "Cria um vínculo entre clínica e procedimento, definindo preço, duração e dentistas habilitados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Procedimento ofertado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clínica ou procedimento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Procedimento já ofertado pela clínica"),
            @ApiResponse(responseCode = "422", description = "Dentista não pertence a clínica")
    })
    ResponseEntity<ClinicProcedureResponseDTO> createProcedure(@RequestBody @Validated ClinicProcedureRequestDTO data);

    @Operation(
            summary = "Atualizar procedimento ofertado",
            description = "Atualiza preço, duração ou dentistas habilitados para um procedimento ofertado pela clínica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Procedimento ofertado atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento ofertado não encontrado")
    })
    ResponseEntity<ClinicProcedureResponseDTO> update(@PathVariable("id") UUID id, @RequestBody @Validated ClinicProcedureUpdateDTO data);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dentista adicionado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dentista, clínica ou procedimento não encontrado"),
            @ApiResponse(responseCode = "422", description = "Dentista não pertence à clínica ou já está associado ao procedimento")
    })
    ResponseEntity<Void> addDentistToClinicProcedure(@PathVariable("clinicProcedureId") UUID clinicProcedureId, @PathVariable("dentistId") UUID dentistId
    );

    @Operation(
            summary = "Listar procedimentos ofertados",
            description = "Retorna todos os procedimentos ofertados pelas clínicas"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    ResponseEntity<List<ClinicProcedureResponseDTO>> findAll();

    @Operation(
            summary = "Remover procedimento ofertado",
            description = "Remove um procedimento ofertado por uma clínica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Procedimento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento ofertado não encontrado"),
            @ApiResponse(responseCode = "422", description = "Procedimento vinculado a agendamentos ou outros recursos")
    })
    ResponseEntity<Void> deleteById(@PathVariable("id") UUID id);

    @Operation(
            summary = "Alterar status do procedimento da clínica",
            description = "Altera o estado (ativo/inativo) de um procedimento ofertado por uma clínica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Status alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento ofertado não encontrado")
    })
    ResponseEntity<Void> changeStatus(@PathVariable("id") UUID id, @RequestBody @Validated ChangeStatusRequestDTO data);
}