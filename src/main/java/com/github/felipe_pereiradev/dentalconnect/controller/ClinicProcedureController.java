package com.github.felipe_pereiradev.dentalconnect.controller;
import com.github.felipe_pereiradev.dentalconnect.controller.Docs.ClinicProcedureControllerDocs;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ChangeStatusRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.service.ClinicProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/clinic-procedures")
@RequiredArgsConstructor
public class ClinicProcedureController implements ClinicProcedureControllerDocs {
    private final ClinicProcedureService clinicProcedureService;

    @PostMapping
    public ResponseEntity<ClinicProcedureResponseDTO> createProcedure(@RequestBody @Validated ClinicProcedureRequestDTO data) {
        ClinicProcedureResponseDTO response  = clinicProcedureService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClinicProcedureResponseDTO> update(@PathVariable("id") UUID id,
                                                       @RequestBody @Validated ClinicProcedureUpdateDTO data) {
        ClinicProcedureResponseDTO response = clinicProcedureService.update(id, data);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/{clinicProcedureId}/dentists/{dentistId}")
    public ResponseEntity<Void> addDentistToClinicProcedure(@PathVariable("clinicProcedureId") UUID clinicProcedureId,
                                                                                  @PathVariable("dentistId") UUID dentistId) {
        clinicProcedureService.addDentistToClinicProcedure(clinicProcedureId, dentistId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ClinicProcedureResponseDTO>> findAll() {
        return ResponseEntity.ok(clinicProcedureService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        clinicProcedureService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") UUID id,
                                             @RequestBody @Validated ChangeStatusRequestDTO data) {
        clinicProcedureService.changeStatus(id, data);
        return ResponseEntity.noContent().build();
    }


}
