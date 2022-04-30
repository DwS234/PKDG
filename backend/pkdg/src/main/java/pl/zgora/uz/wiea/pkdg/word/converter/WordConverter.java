package pl.zgora.uz.wiea.pkdg.word.converter;

import lombok.val;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;
import pl.zgora.uz.wiea.pkdg.word.model.Word;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class WordConverter {

    public static Word convertToModel(WordEntity entity) {
        val word = new Word();
        word.setId(entity.getWordId());
        word.setEntry(entity.getEntry());
        word.setDefinition(entity.getDefinition());
        val wordInSentences = entity.getExamples().stream().map(WordInSentenceConverter::convertToModel).collect(toList());
        word.getExamples().addAll(wordInSentences);
        return word;
    }

    public static WordEntity convertToEntity(Word word) {
        val wordEntity = new WordEntity();
        wordEntity.setEntry(word.getEntry());
        wordEntity.setDefinition(word.getDefinition());
        val wordInSentenceEntities = word.getExamples().stream().map(WordInSentenceConverter::convertToEntity).collect(toSet());
        wordEntity.setExamples(wordInSentenceEntities);
        return wordEntity;
    }
}
