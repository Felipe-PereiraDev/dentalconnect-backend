package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.jwt.TokenResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.user.UserLogin;
import com.github.felipe_pereiradev.dentalconnect.dto.user.UserRegister;
import com.github.felipe_pereiradev.dentalconnect.model.User;
import com.github.felipe_pereiradev.dentalconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createPersonFisica(@RequestBody @Validated UserRegister data) {
        User userResponse = userService.createUser(data);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(userResponse.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }


    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Validated UserLogin userLogin) {
        return ResponseEntity.ok(userService.authentication(userLogin));
    }
}
