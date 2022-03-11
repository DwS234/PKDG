package pl.zgora.uz.wiea.pkdg.exception.model;

import static java.lang.String.format;

public class WordNotFoundException extends RuntimeException {

    public WordNotFoundException(String wordId) {
        super(format("Word for wordId='%s' not found", wordId));
    }
}
