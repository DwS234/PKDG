package pl.zgora.uz.wiea.pkdg.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {

	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	ApiValidationError(String object, String message) {
		this.object = object;
		this.message = message;
	}
}
