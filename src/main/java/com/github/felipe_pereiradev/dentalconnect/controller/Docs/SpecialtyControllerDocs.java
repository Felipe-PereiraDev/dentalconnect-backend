package com.github.felipe_pereiradev.dentalconnect.controller.Docs;

import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Specialties",
        description = "Gerenciamento das especialidades odontológicas"
)
public interface SpecialtyControllerDocs {

    @Operation(
            summary = "Criar especialidade",
            description = "Cadastra uma nova especialidade odontológica no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Especialidade criada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Especialidade já cadastrada"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos")
    })
    ResponseEntity<SpecialtyResponseDTO> create(@RequestBody @Validated SpecialtyRequestDTO data);

    @Operation(
            summary = "Buscar especialidade por ID",
            description = "Retorna os dados de uma especialidade específica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialidade encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    })
    ResponseEntity<SpecialtyResponseDTO> findById(@PathVariable("id") Long id);

    @Operation(
            summary = "Listar especialidades",
            description = "Retorna uma lista paginada de especialidades cadastradas"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    ResponseEntity<Page<SpecialtyResponseDTO>> findAll(Pageable pageable);

    @Operation(
            summary = "Remover especialidade",
            description = "Remove uma especialidade pelo seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Especialidade removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada"),
            @ApiResponse(responseCode = "422", description = "Especialidade vinculada a outros recursos")
    })
    ResponseEntity<Void> deleteById(@PathVariable("id") Long id);
}