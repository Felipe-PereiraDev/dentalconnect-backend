package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.mapper.ClinicMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Address;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.repository.ClinicRepository;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public Clinic createClinic(ClinicRequestDTO data, Address address) {
        Clinic clinic = new Clinic(
                address,
                data.cnpj(),
                data.name(),
                data.phone()
        );
        return clinicRepository.save(clinic);
    }
}
