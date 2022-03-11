package pl.zgora.uz.wiea.pkdg;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import pl.zgora.uz.wiea.pkdg.repetition.model.Repetition;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.model.WordInSentence;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataFactory {

    public static WordInSentence buildWordInSentence(String sentence) {
        val wordInSentence = new WordInSentence();
        wordInSentence.setSentence(sentence);
        return wordInSentence;
    }

    public static List<WordInSentence> buildWordInSentences(String... sentences) {
        return Arrays.stream(sentences).map(DataFactory::buildWordInSentence).collect(toList());
    }

    public static Word buildWord(String wordId, String entry, String definition, List<WordInSentence> examples) {
        val word = new Word();
        word.setWordId(wordId);
        word.setEntry(entry);
        word.setDefinition(definition);
        word.setExamples(examples);
        return word;
    }

    public static Repetition buildRepetition(LocalDate nextDate, double easiness, int consecutiveCorrectAnswers, int timesSeen) {
        val repetition = new Repetition();
        repetition.setNextDate(nextDate);
        repetition.setEasiness(easiness);
        repetition.setConsecutiveCorrectAnswers(consecutiveCorrectAnswers);
        repetition.setTimesSeen(timesSeen);
        return repetition;
    }
}
