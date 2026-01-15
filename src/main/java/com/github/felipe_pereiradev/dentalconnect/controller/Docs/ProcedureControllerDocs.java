package com.github.felipe_pereiradev.dentalconnect.controller.Docs;

import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Procedures",
        description = "Gerenciamento de procedimentos odontológicos"
)
public interface ProcedureControllerDocs {

    @Operation(
            summary = "Criar procedimento",
            description = "Cria um novo procedimento odontológico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Procedimento criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Procedimento já existe")
    })
    ResponseEntity<ProcedureResponseDTO> createProcedure(@RequestBody @Validated ProcedureRequestDTO data);

    @Operation(
            summary = "Atualizar procedimento",
            description = "Atualiza os dados de um procedimento existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Procedimento atualizado"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado")
    })
    ResponseEntity<ProcedureResponseDTO> update(@PathVariable("id") UUID id, @RequestBody @Validated ProcedureUpdateDTO data);

    @Operation(
            summary = "Listar procedimentos",
            description = "Retorna todos os procedimentos ativos"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    ResponseEntity<List<ProcedureResponseDTO>> findAll();

    @Operation(
            summary = "Listar procedimentos pro nome",
            description = "Busca procedimentos ativos pelo nome (mínimo 3 caracteres)"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    ResponseEntity<List<ProcedureResponseDTO>> findAllByName(@RequestParam("name") String name);

    @Operation(
            summary = "Remover procedimento",
            description = "Remove um procedimento pelo ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Procedimento removido"),
        @ApiResponse(responseCode = "404", description = "Procedimento não encontrado"),
        @ApiResponse(responseCode = "422", description = "Procedimento vinculado a outro recurso")
    })
    ResponseEntity<Void> deleteById(@PathVariable("id") UUID id);

}
