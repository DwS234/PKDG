package pl.zgora.uz.wiea.pkdg;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.zgora.uz.wiea.pkdg.repetition.model.Repetition;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.model.WordInSentence;

import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertUtils {

    public static void assertWord(Word word, String entry,String definition, String... exampleSentences) {
        assertThat(word).isNotNull();
        assertThat(word.getWordId()).isNotBlank();
        assertThat(word.getEntry()).isEqualTo(entry);
        assertThat(word.getDefinition()).isEqualTo(definition);
        assertThat(word.getExamples().stream().map(WordInSentence::getSentence)
                .collect(toList())).containsExactly(exampleSentences);
    }

    public static void assertRepetition(Repetition repetition, LocalDate nextDate, double easiness,
                                        int consecutiveCorrectAnswers, int timesSeen, int lastIntervalDays) {
        assertThat(repetition).isNotNull();
        assertThat(repetition.getRepetitionId()).isNotBlank();
        assertThat(repetition.getNextDate()).isEqualTo(nextDate);
        assertThat(repetition.getEasiness()).isEqualTo(easiness);
        assertThat(repetition.getConsecutiveCorrectAnswers()).isEqualTo(consecutiveCorrectAnswers);
        assertThat(repetition.getTimesSeen()).isEqualTo(timesSeen);
        assertThat(repetition.getLastIntervalDays()).isEqualTo(lastIntervalDays);
    }
}
