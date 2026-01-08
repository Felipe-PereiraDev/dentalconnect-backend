package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.clinic.ClinicRegistrationRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.employee.EmployeeRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.github.felipe_pereiradev.dentalconnect.enums.RoleType.*;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final ClinicService clinicService;
    private final AddressService addressService;

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
}
