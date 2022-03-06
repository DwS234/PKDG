package pl.zgora.uz.wiea.pkdg.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(final String username) {
		super(String.format("User with username='%s' not found", username));
	}
}
