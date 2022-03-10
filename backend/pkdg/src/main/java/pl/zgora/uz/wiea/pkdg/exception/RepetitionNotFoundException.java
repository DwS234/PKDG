package pl.zgora.uz.wiea.pkdg.exception;

import static java.lang.String.format;

public class RepetitionNotFoundException extends RuntimeException {

    public RepetitionNotFoundException(String username, String repetitionId) {
        super(format("Repetition for username='%s' and repetitionId='%s' not found", username, repetitionId));
    }
}
