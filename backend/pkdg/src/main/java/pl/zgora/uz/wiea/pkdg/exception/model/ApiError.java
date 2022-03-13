package pl.zgora.uz.wiea.pkdg.exception.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
public class ApiError {

	private HttpStatus status;

	private String message;

	private List<ApiSubError> subErrors;

	public ApiError(HttpStatus status) {
		this.status = status;
	}

	public ApiError(HttpStatus status, Throwable ex) {
		this.status = status;
		this.message = "Unexpected error";
	}

	public ApiError(HttpStatus status, String message, Throwable ex) {
		this.status = status;
		this.message = message;
	}
}
