package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.model.Employee;
import com.github.felipe_pereiradev.dentalconnect.model.User;
import com.github.felipe_pereiradev.dentalconnect.repository.EmployeeRepository;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee create(String name, String phone, String jobTitle, Clinic clinic, User user) {
        Employee employee = new Employee(
                name,
                phone,
                PersonType.EMPLOYEE,
                clinic,
                jobTitle,
                user
        );
        return employeeRepository.save(employee);
    }


}
