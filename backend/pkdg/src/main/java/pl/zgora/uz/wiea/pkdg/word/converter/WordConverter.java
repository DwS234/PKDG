package pl.zgora.uz.wiea.pkdg.word.converter;

import lombok.val;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;
import pl.zgora.uz.wiea.pkdg.word.model.Word;

import static java.util.stream.Collectors.toList;

public class WordConverter {

    public static Word convertToModel(WordEntity entity) {
        val word = new Word();
        word.setEntry(entity.getEntry());
        word.setDefinition(entity.getDefinition());
        val exampleModels = entity.getExamples().stream().map(WordInSentenceConverter::convertToModel).collect(toList());
        word.getExamples().addAll(exampleModels);
        return word;
    }
}
