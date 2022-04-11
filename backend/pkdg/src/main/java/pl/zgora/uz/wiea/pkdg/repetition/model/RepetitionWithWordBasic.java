package pl.zgora.uz.wiea.pkdg.repetition.model;

import lombok.Data;
import pl.zgora.uz.wiea.pkdg.word.model.WordBasic;

@Data
public class RepetitionWithWordBasic {
	private Repetition repetition;

	private WordBasic word;
}
