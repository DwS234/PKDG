package pl.zgora.uz.wiea.pkdg.repetition.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class WordInRepetition {
	private final String wordId;
	private final String repetitionId;
}
