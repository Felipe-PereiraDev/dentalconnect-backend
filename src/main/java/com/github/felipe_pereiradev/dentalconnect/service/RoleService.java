package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.enums.RoleType;
import com.github.felipe_pereiradev.dentalconnect.exception.EntityNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.model.Role;
import com.github.felipe_pereiradev.dentalconnect.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRole(RoleType roleType) {
        return roleRepository.findByRoleType(roleType)
                .orElseThrow(() -> new EntityNotFoundException("ROLE NOT EXISTS"));
    }
}
