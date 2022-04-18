package pl.zgora.uz.wiea.pkdg.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zgora.uz.wiea.pkdg.auth.model.LoginRequest;
import pl.zgora.uz.wiea.pkdg.auth.model.LoginResponse;
import pl.zgora.uz.wiea.pkdg.auth.model.RegisterRequest;
import pl.zgora.uz.wiea.pkdg.config.jwt.JwtTokenUtil;
import pl.zgora.uz.wiea.pkdg.exception.UserAlreadyExistsException;
import pl.zgora.uz.wiea.pkdg.user.converter.UserConverter;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;
import pl.zgora.uz.wiea.pkdg.user.model.AppUserDetails;
import pl.zgora.uz.wiea.pkdg.user.model.User;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;

import static pl.zgora.uz.wiea.pkdg.user.model.UserRole.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public User registerUser(final RegisterRequest registerRequest) {
        final String username = registerRequest.getUsername();
        if (userRepository.existsById(username)) {
            throw new UserAlreadyExistsException(username);
        }

        final UserEntity userEntity = userConverter.convertRegisterRequestToEntity(registerRequest);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(USER);
        userRepository.save(userEntity);

        return userConverter.convertEntityToModel(userEntity);
    }

    public LoginResponse authenticateUser(final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        final AppUserDetails user = (AppUserDetails) authentication.getPrincipal();

        final LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(user.getEmail());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setToken(jwtTokenUtil.generateAccessToken(user.getUsername()));
        loginResponse.setRole(user.getAuthorities().iterator().next().getAuthority());
        return loginResponse;
    }
}
