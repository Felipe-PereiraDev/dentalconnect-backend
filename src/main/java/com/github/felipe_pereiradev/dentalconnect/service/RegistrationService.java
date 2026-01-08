package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRegistrationRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.dentist.DentistRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.employee.EmployeeRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.patient.PatientRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.felipe_pereiradev.dentalconnect.enums.RoleType.*;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final ClinicService clinicService;
    private final AddressService addressService;
    private final PatientService patientService;
    private final DentistService dentistService;
    private final SpecialtyService specialtyService;

    @Transactional
    public void registerClinicWithOwner(ClinicRegistrationRequestDTO data) {
        Address address = addressService.create(data.clinic().address());
        Clinic clinic = clinicService.createClinic(data.clinic(), address);
        List<Role> roleList = roleService.getRoleList(List.of(ROLE_OWNER, ROLE_EMPLOYEE, ROLE_USER));
        User user = userService.create(data.email(), data.password(), roleList);
        Employee employee = employeeService.create(data.name(), data.phone(), "Clinic Owner", clinic, user);
    }

    @Transactional
    public void registerEmployee(EmployeeRequestDTO data) {
        Clinic clinic = clinicService.findById(data.clinicId());
        List<Role> roleList = roleService.getRoleList(List.of(ROLE_EMPLOYEE, ROLE_USER));
        User user = userService.create(data.email(), data.password(), roleList);
        Employee employee = employeeService.create(data.name(), data.phone(), data.jobTitle(), clinic, user);
    }

    @Transactional
    public void registerPatient(PatientRequestDTO data) {
        Address address = addressService.create(data.address());
        List<Role> roleList = roleService.getRoleList(List.of(ROLE_PATIENT, ROLE_USER));
        User user = userService.create(data.email(), data.password(), roleList);
        Patient patient = patientService.create(data.name(), data.phone(), data.cpf(), address, user);
    }

    @Transactional
    public void registerDentist(DentistRequestDTO data) {
        Clinic clinic = clinicService.findById(data.clinicId());
        List<Role> roleList = roleService.getRoleList(List.of(ROLE_PATIENT, ROLE_USER));
        User user = userService.create(data.email(), data.password(), roleList);
        List<Specialty> specialtyList = specialtyService.findAllById(data.specialties());
        Dentist dentist = dentistService.create(data.name(), data.phone(), data.licenseNumber(), clinic, user, specialtyList);
    }
}
