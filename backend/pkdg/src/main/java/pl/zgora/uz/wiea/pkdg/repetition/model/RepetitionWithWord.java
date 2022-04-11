package pl.zgora.uz.wiea.pkdg.repetition.model;

import lombok.Data;
import pl.zgora.uz.wiea.pkdg.word.model.Word;

@Data
public class RepetitionWithWord {
	private Repetition repetition;

	private Word word;
}
