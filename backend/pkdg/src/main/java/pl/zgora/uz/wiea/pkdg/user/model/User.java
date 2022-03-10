package pl.zgora.uz.wiea.pkdg.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class User {

	private String username;

	@JsonIgnore
	private String password;

	private String email;
}
