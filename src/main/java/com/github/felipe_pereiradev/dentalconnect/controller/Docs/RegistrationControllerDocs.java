package com.github.felipe_pereiradev.dentalconnect.controller.Docs;

import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRegistrationRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.dentist.DentistRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.employee.EmployeeRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.patient.PatientRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Registros", description = "Endpoints responsáveis pelo cadastro inicial de clínicas, profissionais e pacientes")
public interface RegistrationControllerDocs {

    @Operation(summary = "Registrar clínica com proprietário", description = "Realiza o cadastro de uma clínica juntamente com o usuário proprietário responsável")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Clínica registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Clínica já cadastrada")
    })
    ResponseEntity<?> registerClinicWithOwner(@RequestBody @Validated ClinicRegistrationRequestDTO data);

    @Operation(summary = "Registrar funcionário", description = "Cadastra um funcionário vinculado a uma clínica existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    ResponseEntity<?> registerEmployee(@RequestBody @Validated EmployeeRequestDTO data);

    @Operation(summary = "Registrar paciente", description = "Realiza o cadastro de um paciente no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Paciente já cadastrado")
    })
    ResponseEntity<?> registerPatient(@RequestBody @Validated PatientRequestDTO data);

    @Operation(summary = "Registrar dentista", description = "Cadastra um dentista e o vincula a uma clínica")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dentista registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    ResponseEntity<?> registerDentist(@RequestBody @Validated DentistRequestDTO data);
}