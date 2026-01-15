package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.controller.Docs.ProcedureControllerDocs;
import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.service.ProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/procedures")
@RequiredArgsConstructor
public class ProcedureController implements ProcedureControllerDocs {
    private final ProcedureService procedureService;

    @PostMapping
    public ResponseEntity<ProcedureResponseDTO> createProcedure(@RequestBody @Validated ProcedureRequestDTO data) {
        ProcedureResponseDTO procedureResponseDTO = procedureService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(procedureResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProcedureResponseDTO> update(@PathVariable("id") UUID id,
                                                       @RequestBody @Validated ProcedureUpdateDTO data) {
        ProcedureResponseDTO procedureResponseDTO = procedureService.update(id, data);
        return ResponseEntity.ok(procedureResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProcedureResponseDTO>> findAll() {
        return ResponseEntity.ok(procedureService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProcedureResponseDTO>> findAllByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(procedureService.findAllByName(name));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        procedureService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
