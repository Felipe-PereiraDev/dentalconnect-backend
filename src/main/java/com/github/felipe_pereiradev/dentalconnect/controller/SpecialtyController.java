package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.specialty.SpecialtyResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/specialties")
@RequiredArgsConstructor
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    @PostMapping
    public ResponseEntity<SpecialtyResponseDTO> create(@RequestBody @Validated SpecialtyRequestDTO data) {
        SpecialtyResponseDTO specialtyResponseDTO = specialtyService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyResponseDTO);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<SpecialtyResponseDTO> findById(@PathVariable("id") Long id) {
        SpecialtyResponseDTO specialtyResponseDTO = specialtyService.findById(id);
        return ResponseEntity.ok(specialtyResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<SpecialtyResponseDTO>> findAll(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<SpecialtyResponseDTO> specialtyResponseList = specialtyService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyResponseList);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        specialtyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
