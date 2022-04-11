package pl.zgora.uz.wiea.pkdg.repetition.converter;

import lombok.val;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;
import pl.zgora.uz.wiea.pkdg.repetition.model.RepetitionWithWord;
import pl.zgora.uz.wiea.pkdg.word.converter.WordConverter;

public class RepetitionWithWordConverter {

	public static RepetitionWithWord convertToModel(RepetitionEntity entity) {
		val repetition = RepetitionConverter.convertToModel(entity);

		val word = entity.getWord();
		val wordModel = WordConverter.convertToModel(word);

		val repetitionWithWord = new RepetitionWithWord();
		repetitionWithWord.setRepetition(repetition);
		repetitionWithWord.setWord(wordModel);
		return repetitionWithWord;
	}
}
