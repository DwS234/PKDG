package pl.zgora.uz.wiea.pkdg.repetition.converter;

import lombok.val;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;
import pl.zgora.uz.wiea.pkdg.repetition.model.RepetitionWithWord;
import pl.zgora.uz.wiea.pkdg.word.model.WordBasic;

public class RepetitionWithWordConverter {

	public static RepetitionWithWord convertToModel(RepetitionEntity entity) {
		val repetition = RepetitionConverter.convertToModel(entity);
		val wordBasic = new WordBasic();
		val word = entity.getWord();
		wordBasic.setDefinition(word.getDefinition());
		wordBasic.setEntry(word.getEntry());
		wordBasic.setWordId(word.getWordId());

		val repetitionWithWord = new RepetitionWithWord();
		repetitionWithWord.setRepetition(repetition);
		repetitionWithWord.setWord(wordBasic);
		return repetitionWithWord;
	}
}
