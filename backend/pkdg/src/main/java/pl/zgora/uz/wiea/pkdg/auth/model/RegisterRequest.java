package pl.zgora.uz.wiea.pkdg.auth.model;

import lombok.Data;

@Data
public class RegisterRequest {

	private String username;

	private String password;

	private String email;
}
