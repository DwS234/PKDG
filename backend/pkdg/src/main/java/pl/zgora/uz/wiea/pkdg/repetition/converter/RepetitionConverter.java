package pl.zgora.uz.wiea.pkdg.repetition.converter;

import lombok.val;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;
import pl.zgora.uz.wiea.pkdg.repetition.model.Repetition;

public class RepetitionConverter {

    public static Repetition convertToModel(RepetitionEntity entity) {
        val repetition = new Repetition();
        repetition.setRepetitionId(entity.getRepetitionId());
        repetition.setNextDate(entity.getNextDate());
        repetition.setEasiness(entity.getEasiness());
        repetition.setConsecutiveCorrectAnswers(entity.getConsecutiveCorrectAnswers());
        repetition.setTimesSeen(entity.getTimesSeen());
        return repetition;
    }

    public static RepetitionEntity convertToEntity(Repetition repetition) {
        val entity = new RepetitionEntity();
        entity.setNextDate(repetition.getNextDate());
        entity.setEasiness(repetition.getEasiness());
        entity.setConsecutiveCorrectAnswers(repetition.getConsecutiveCorrectAnswers());
        entity.setTimesSeen(repetition.getTimesSeen());
        return entity;
    }
}
