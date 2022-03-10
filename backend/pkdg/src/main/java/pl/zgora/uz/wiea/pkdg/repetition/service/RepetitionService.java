package pl.zgora.uz.wiea.pkdg.repetition.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.zgora.uz.wiea.pkdg.exception.RepetitionNotFoundException;
import pl.zgora.uz.wiea.pkdg.exception.UserNotFoundException;
import pl.zgora.uz.wiea.pkdg.repetition.converter.RepetitionConverter;
import pl.zgora.uz.wiea.pkdg.repetition.model.Repetition;
import pl.zgora.uz.wiea.pkdg.repetition.repository.RepetitionRepository;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static pl.zgora.uz.wiea.pkdg.repetition.converter.RepetitionConverter.convertToEntity;
import static pl.zgora.uz.wiea.pkdg.repetition.converter.RepetitionConverter.convertToModel;

@Service
@RequiredArgsConstructor
public class RepetitionService {

    private final RepetitionRepository repetitionRepository;

    private final UserRepository userRepository;

    @Transactional
    public Repetition createRepetition(String username, Repetition repetition) {
        val userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UserNotFoundException(username);
        }

        var repetitionEntity = convertToEntity(repetition);
        repetitionEntity.setRepetitionId(UUID.randomUUID().toString());
        repetitionEntity.setUser(userEntity);
        repetitionEntity = repetitionRepository.save(repetitionEntity);

        return convertToModel(repetitionEntity);
    }

    public List<Repetition> getRepetitionsByUsername(String username) {
        val repetitions = repetitionRepository.findAllByUsername(username);
        return repetitions.stream().map(RepetitionConverter::convertToModel).collect(toList());
    }

    @Transactional
    public Repetition updateRepetition(String username, String repetitionId, Repetition repetition) {
        var repetitionEntity = repetitionRepository.findByUsernameAndRepetitionId(username, repetitionId);
        if (repetitionEntity == null) {
            throw new RepetitionNotFoundException(username, repetitionId);
        }

        repetitionEntity.setNextDate(repetition.getNextDate());
        repetitionEntity.setConsecutiveCorrectAnswers(repetition.getConsecutiveCorrectAnswers());
        repetitionEntity.setTimesSeen(repetition.getTimesSeen());
        repetitionEntity = repetitionRepository.save(repetitionEntity);

        return convertToModel(repetitionEntity);
    }
}
