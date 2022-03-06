package pl.zgora.uz.wiea.pkdg.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zgora.uz.wiea.pkdg.auth.service.AuthService;
import pl.zgora.uz.wiea.pkdg.auth.model.LoginRequest;
import pl.zgora.uz.wiea.pkdg.auth.model.LoginResponse;
import pl.zgora.uz.wiea.pkdg.auth.model.RegisterRequest;
import pl.zgora.uz.wiea.pkdg.user.model.User;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
		final User registeredUser = authService.registerUser(registerRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		final LoginResponse loginResponse = authService.authenticateUser(loginRequest);
		return ResponseEntity.ok().body(loginResponse);
	}
}
