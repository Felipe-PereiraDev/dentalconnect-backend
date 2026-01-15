package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ChangeStatusRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.dentist.DentistIdDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.ConflictException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.exception.UnprocessableEntityException;
import com.github.felipe_pereiradev.dentalconnect.mapper.ClinicProcedureMapper;
import com.github.felipe_pereiradev.dentalconnect.model.*;
import com.github.felipe_pereiradev.dentalconnect.repository.ClinicProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicProcedureService {
    private final ClinicProcedureRepository clinicProcedureRepository;
    private final ClinicProcedureMapper clinicProcedureMapper;
    private final ClinicService clinicService;
    private final ProcedureService procedureService;
    private final DentistService dentistService;

    public ClinicProcedure findByClinicIdAndProcedureId(UUID clinicId, UUID procedureId) {
        return clinicProcedureRepository.findByClinicIdAndProcedureId(clinicId, procedureId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("clinicProcedure not found")
                );

    }

    public ClinicProcedureResponseDTO create(ClinicProcedureRequestDTO data) {
        Clinic clinic = clinicService.findById(data.clinicId());
        Procedure procedure = procedureService.findById(data.procedureId());

        if (clinicProcedureRepository.existsByClinicIdAndProcedureId(data.clinicId(), data.procedureId())) {
            throw new ConflictException("the procedure already exists at the clinic");
        }

        List<Dentist> dentistList = validateAndGetDentistsForClinic(data.dentists(), clinic);

        ClinicProcedure createdclinicProcedure = new ClinicProcedure(
                data.duration(),
                data.price(),
                clinic,
                procedure
        );
        clinicProcedureRepository.save(createdclinicProcedure);
        return clinicProcedureMapper.toResponseDTO(createdclinicProcedure);
    }

    private List<Dentist> validateAndGetDentistsForClinic(List<DentistIdDTO> dentistIdList, Clinic clinic) {
        List<Dentist> dentistList = dentistIdList.stream()
                .map(d -> dentistService.findById(d.id()))
                .toList();

        boolean allBelong = dentistList.stream().allMatch(d -> d.getClinic().getId().equals(clinic.getId()));
        if (!allBelong) {
            throw new UnprocessableEntityException("Dentist does not belong to the clinic");
        }
        return dentistList;
    }

    public ClinicProcedureResponseDTO update(UUID id, ClinicProcedureUpdateDTO data) {
        ClinicProcedure clinicProcedure = findById(id);
        clinicProcedure.update(data);
        clinicProcedureRepository.save(clinicProcedure);
        return clinicProcedureMapper.toResponseDTO(clinicProcedure);
    }

    public List<ClinicProcedureResponseDTO> findAll() {
        List<ClinicProcedure> clinicProcedureList = clinicProcedureRepository.findAllByActiveTrue();
        return clinicProcedureMapper.toResponseDTOList(clinicProcedureList);
    }

    public void deleteById(UUID id) {
        if (!clinicProcedureRepository.existsById(id)) {
            throw new ResourceNotFoundException("ClinicProcedure not found");
        }
        try {
            clinicProcedureRepository.deleteById(id);
            clinicProcedureRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new UnprocessableEntityException("ClinicProcedure cannot be deleted because it is referenced by another resource");
        }
    }

    public void addDentistToClinicProcedure(UUID clinicProcedureId, UUID dentistId) {
        ClinicProcedure clinicProcedure = findById(clinicProcedureId);
        Dentist dentist = dentistService.findById(dentistId);

        if (!dentist.getClinic().getId().equals(clinicProcedure.getClinic().getId())) {
            throw new UnprocessableEntityException("Dentist does not belong to the clinic");
        }

        boolean isAssociated = clinicProcedure.getDentistClinicProcedureList().stream()
                .anyMatch(dcp -> dcp.getDentist().equals(dentist) && dcp.isActive());

        if (isAssociated) {
            throw new UnprocessableEntityException("Dentist is already associated with the procedure");
        }
        DentistClinicProcedure dentistClinicProcedure = new DentistClinicProcedure(dentist, clinicProcedure);
        clinicProcedure.getDentistClinicProcedureList().add(dentistClinicProcedure);
        clinicProcedureRepository.save(clinicProcedure);
    }


    public ClinicProcedure findById(UUID id) {
        return clinicProcedureRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("ClinicProcedure not found")
                );
    }


    public void changeStatus(UUID id, ChangeStatusRequestDTO data) {
        ClinicProcedure clinicProcedure = findById(id);
        clinicProcedure.setActive(data.active());
        clinicProcedureRepository.save(clinicProcedure);
    }
}
