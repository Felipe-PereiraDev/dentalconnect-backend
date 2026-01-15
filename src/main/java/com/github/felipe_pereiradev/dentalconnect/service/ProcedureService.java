package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.ConflictException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.exception.UnprocessableEntityException;
import com.github.felipe_pereiradev.dentalconnect.mapper.ProcedureMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Procedure;
import com.github.felipe_pereiradev.dentalconnect.repository.ProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcedureService {

    private final ProcedureRepository procedureRepository;
    private final ProcedureMapper procedureMapper;

    public ProcedureResponseDTO create(ProcedureRequestDTO data) {
        throwIfNameExists(data.name());
        Procedure createdProcedure = new Procedure(
                data.name().trim(),
                data.description()
        );
        procedureRepository.save(createdProcedure);
        return procedureMapper.toResponseDTO(createdProcedure);
    }

    public List<ProcedureResponseDTO> findAll() {
        List<Procedure> procedureList = procedureRepository.findAllByActiveTrue();
        return procedureMapper.toResponseDTOList(procedureList);
    }

    public List<ProcedureResponseDTO> findAllByName(String name) {
        List<Procedure> procedureList = new ArrayList<>();
        if (name.length() > 2)
            procedureList = procedureRepository.findAllByName(name);
        return procedureMapper.toResponseDTOList(procedureList);
    }

    public void deleteById(UUID id) {
        if (!procedureRepository.existsById(id)) {
            throw new ResourceNotFoundException("procedure not found");
        }
        try {
            procedureRepository.deleteById(id);
            procedureRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new UnprocessableEntityException("specialty cannot be deleted because it is referenced by another resource");
        }
    }

    public ProcedureResponseDTO update(UUID id, ProcedureUpdateDTO data) {
        Procedure procedure = findById(id);
        throwIfNameExistsAndIdNot(data.name(), procedure.getId());
        procedure.update(data);
        procedureRepository.save(procedure);
        return procedureMapper.toResponseDTO(procedure);
    }

    public Procedure findById(UUID id) {
        return procedureRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("procedure not found")
                );
    }

    public void throwIfNameExists(String name) {
        if (name != null && !name.isBlank() && procedureRepository.existsByName(name)) {
            throw new ConflictException("a procedure with this name already exists");
        }
    }

    public void throwIfNameExistsAndIdNot(String name, UUID id) {
        if (name != null && !name.isBlank() && procedureRepository.existsByNameAndIdNot(name, id)) {
            throw new ConflictException("a procedure with this name already exists");
        }
    }
}
