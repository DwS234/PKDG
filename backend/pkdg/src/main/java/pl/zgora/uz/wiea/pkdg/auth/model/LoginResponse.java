package pl.zgora.uz.wiea.pkdg.auth.model;

import lombok.Data;

@Data
public class LoginResponse {

	private String username;

	private String email;

	private String token;

	private String role;
}
