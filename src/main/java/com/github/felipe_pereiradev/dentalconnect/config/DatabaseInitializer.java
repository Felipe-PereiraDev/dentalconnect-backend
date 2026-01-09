package com.github.felipe_pereiradev.dentalconnect.config;

import com.github.felipe_pereiradev.dentalconnect.enums.RoleType;
import com.github.felipe_pereiradev.dentalconnect.model.Role;
import com.github.felipe_pereiradev.dentalconnect.service.RoleService;
import com.github.felipe_pereiradev.dentalconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserService userService;

    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
    }

    private void createAdmin() {
        List<Role> roleList = roleService.getRoleList(List.of(RoleType.ROLE_ADMIN, RoleType.ROLE_USER));
        userService.create("admin@admin.com", "admin123", roleList);
    }
}
