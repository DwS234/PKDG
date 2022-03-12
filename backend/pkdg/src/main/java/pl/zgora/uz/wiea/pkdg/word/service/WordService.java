package pl.zgora.uz.wiea.pkdg.word.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.zgora.uz.wiea.pkdg.repetition.repository.RepetitionRepository;
import pl.zgora.uz.wiea.pkdg.user.repository.UserRepository;
import pl.zgora.uz.wiea.pkdg.word.converter.WordConverter;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toUnmodifiableList;
import static pl.zgora.uz.wiea.pkdg.word.converter.WordConverter.convertToEntity;
import static pl.zgora.uz.wiea.pkdg.word.converter.WordConverter.convertToModel;

@Service
@RequiredArgsConstructor
public class WordService {

    private final UserRepository userRepository;

    private final WordRepository wordRepository;

    private final RepetitionRepository repetitionRepository;

    @Transactional
    public Word createWord(Word word) {
        val wordEntity = convertToEntity(word);
        wordEntity.setWordId(UUID.randomUUID().toString());
        wordEntity.getExamples().forEach(e -> e.setWord(wordEntity));
        return convertToModel(wordRepository.save(wordEntity));
    }
    
    @Transactional
    public List<Word> getWords(Pageable pageable) {
        return wordRepository.findAll(pageable).getContent().stream().map(WordConverter::convertToModel).collect(toUnmodifiableList());
    }
}
