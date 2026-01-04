package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.config.security.JwtServiceConfig;
import com.github.felipe_pereiradev.dentalconnect.dto.jwt.TokenResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.user.UserLogin;
import com.github.felipe_pereiradev.dentalconnect.dto.user.UserRegister;
import com.github.felipe_pereiradev.dentalconnect.enums.RoleType;
import com.github.felipe_pereiradev.dentalconnect.enums.UserStatusEnum;
import com.github.felipe_pereiradev.dentalconnect.exception.DuplicateResourceException;
import com.github.felipe_pereiradev.dentalconnect.exception.ForbiddenException;
import com.github.felipe_pereiradev.dentalconnect.exception.InvalidLoginException;
import com.github.felipe_pereiradev.dentalconnect.model.Role;
import com.github.felipe_pereiradev.dentalconnect.model.User;
import com.github.felipe_pereiradev.dentalconnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final JwtServiceConfig jwtServiceConfig;

    private final AuthenticationManager authenticationManager;

    public TokenResponseDTO authentication(UserLogin userLogin ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password())
            );
            String token = jwtServiceConfig.generateToken(authentication);
            return new TokenResponseDTO(token);
        } catch (DisabledException ex) {
            throw new ForbiddenException("Account is not active");
        }catch (BadCredentialsException ex) {
            throw new InvalidLoginException("Email or password invalid");
        }
    }

    @Transactional
    public User createUser(UserRegister data) {
        Optional<User> user = userRepository.findByEmail(data.email());

        if (user.isPresent() && user.get().getStatus() != UserStatusEnum.PENDING) {
            throw new DuplicateResourceException("The email %s is unavailable".formatted(data.email()));
        }

        Role roleUser = roleService.getRole(RoleType.ROLE_USER);

        User newUser = new User(
                data.email(),
                passwordEncoder.encode(data.password()),
                List.of(roleUser)
        );
        return userRepository.save(newUser);
    }

}
