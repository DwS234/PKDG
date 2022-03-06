package pl.zgora.uz.wiea.pkdg.auth.model;

import lombok.Data;

@Data
public class LoginRequest {

	private String username;

	private String password;
}
