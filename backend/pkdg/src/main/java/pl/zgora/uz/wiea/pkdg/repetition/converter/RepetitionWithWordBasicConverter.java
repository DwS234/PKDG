package pl.zgora.uz.wiea.pkdg.repetition.converter;

import lombok.val;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;
import pl.zgora.uz.wiea.pkdg.repetition.model.RepetitionWithWordBasic;
import pl.zgora.uz.wiea.pkdg.word.model.WordBasic;

public class RepetitionWithWordBasicConverter {

	public static RepetitionWithWordBasic convertToModel(RepetitionEntity entity) {
		val repetition = RepetitionConverter.convertToModel(entity);
		val wordBasic = new WordBasic();
		val word = entity.getWord();
		wordBasic.setDefinition(word.getDefinition());
		wordBasic.setEntry(word.getEntry());
		wordBasic.setWordId(word.getWordId());

		val repetitionWithWordBasic = new RepetitionWithWordBasic();
		repetitionWithWordBasic.setRepetition(repetition);
		repetitionWithWordBasic.setWord(wordBasic);
		return repetitionWithWordBasic;
	}
}
