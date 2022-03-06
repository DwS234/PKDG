package pl.zgora.uz.wiea.pkdg.exception;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException(final String username) {
		super(String.format("User with username='%s' already exists", username));
	}
}
