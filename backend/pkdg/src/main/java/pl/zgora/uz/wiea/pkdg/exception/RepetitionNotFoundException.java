package pl.zgora.uz.wiea.pkdg.exception;

import static java.lang.String.format;

public class RepetitionNotFoundException extends RuntimeException {

    public RepetitionNotFoundException(String repetitionId) {
        super(format("Repetition for repetitionId='%s' not found", repetitionId));
    }
}
