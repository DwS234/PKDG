package pl.zgora.uz.wiea.pkdg.repetition.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.zgora.uz.wiea.pkdg.exception.RepetitionNotFoundException;
import pl.zgora.uz.wiea.pkdg.exception.UserNotFoundException;
import pl.zgora.uz.wiea.pkdg.exception.WordNotFoundException;
import pl.zgora.uz.wiea.pkdg.repetition.converter.RepetitionWithWordConverter;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;
import pl.zgora.uz.wiea.pkdg.repetition.model.Repetition;
import pl.zgora.uz.wiea.pkdg.repetition.model.RepetitionWithWord;
import pl.zgora.uz.wiea.pkdg.repetition.model.WordInRepetition;
import pl.zgora.uz.wiea.pkdg.repetition.repository.RepetitionRepository;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static pl.zgora.uz.wiea.pkdg.repetition.converter.RepetitionConverter.convertToEntity;
import static pl.zgora.uz.wiea.pkdg.repetition.converter.RepetitionConverter.convertToModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepetitionService {

    private final RepetitionRepository repetitionRepository;

    private final UserRepository userRepository;

    private final WordRepository wordRepository;

    @Transactional
    public Repetition createRepetition(String username, String wordId, Repetition repetition) {
        val userEntity = getUserEntityByUsernameOrThrowException(username);
        val wordEntity = getWordEntityByWordIdOrThrowException(wordId);

        var repetitionEntity = convertToEntity(repetition);
        repetitionEntity.setRepetitionId(UUID.randomUUID().toString());
        repetitionEntity.setUser(userEntity);
        repetitionEntity.setWord(wordEntity);
        repetitionEntity = repetitionRepository.save(repetitionEntity);

        log.debug("Repetition created for username='{}', wordId='{} with data={}", username, wordId, repetitionEntity);

        return convertToModel(repetitionEntity);
    }

    public List<RepetitionWithWord> getRepetitionsByUsername(String username) {
        val repetitions = repetitionRepository.findAllByUsername(username);
        return repetitions.stream().map(RepetitionWithWordConverter::convertToModel).collect(toList());
    }

    @Transactional
    public Repetition updateRepetition(String repetitionId, Repetition repetition) {
        var repetitionEntity = getRepetitionEntityByRepetitionIdOrThrowException(repetitionId);
        repetitionEntity.setNextDate(repetition.getNextDate());
        repetitionEntity.setConsecutiveCorrectAnswers(repetition.getConsecutiveCorrectAnswers());
        repetitionEntity.setTimesSeen(repetition.getTimesSeen());
        repetitionEntity.setLastIntervalDays(repetition.getLastIntervalDays());
        repetitionEntity = repetitionRepository.save(repetitionEntity);

        log.debug("Repetition updated for repetitionId='{}' with data={}", repetitionId, repetitionEntity);

        return convertToModel(repetitionEntity);
    }

    public List<WordInRepetition> getRepetitionsByWordsIds(List<String> wordsIds, String username) {
        return repetitionRepository.findAllByWordsIdsAndUsername(wordsIds, username);
    }

    @Transactional
    public void deleteRepetition(String repetitionId) {
        RepetitionEntity repetitionEntity = getRepetitionEntityByRepetitionIdOrThrowException(repetitionId);
        repetitionRepository.delete(repetitionEntity);
    }

    private UserEntity getUserEntityByUsernameOrThrowException(String username) {
        val userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UserNotFoundException(username);
        }
        return userEntity;
    }

    private WordEntity getWordEntityByWordIdOrThrowException(String wordId) {
        val wordEntity = wordRepository.findByWordId(wordId);
        if (wordEntity == null) {
            throw new WordNotFoundException(wordId);
        }
        return wordEntity;
    }

    private RepetitionEntity getRepetitionEntityByRepetitionIdOrThrowException(String repetitionId) {
        val repetitionEntity = repetitionRepository.findByRepetitionId(repetitionId);
        if (repetitionEntity == null) {
            throw new RepetitionNotFoundException(repetitionId);
        }
        return repetitionEntity;
    }
}
