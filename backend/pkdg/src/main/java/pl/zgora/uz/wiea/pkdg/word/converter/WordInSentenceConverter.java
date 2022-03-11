package pl.zgora.uz.wiea.pkdg.word.converter;

import lombok.val;
import pl.zgora.uz.wiea.pkdg.word.entity.WordInSentenceEntity;
import pl.zgora.uz.wiea.pkdg.word.model.WordInSentence;

public class WordInSentenceConverter {

    public static WordInSentence convertToModel(WordInSentenceEntity entity) {
        val wordInSentence = new WordInSentence();
        wordInSentence.setSentence(entity.getSentence());
        return wordInSentence;
    }

    public static WordInSentenceEntity convertToEntity(WordInSentence wordInSentence) {
        val wordInSentenceEntity = new WordInSentenceEntity();
        wordInSentenceEntity.setSentence(wordInSentence.getSentence());
        return wordInSentenceEntity;
    }
}
