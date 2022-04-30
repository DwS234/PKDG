package pl.zgora.uz.wiea.pkdg.word.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.zgora.uz.wiea.pkdg.exception.WordNotFoundException;
import pl.zgora.uz.wiea.pkdg.word.converter.WordConverter;
import pl.zgora.uz.wiea.pkdg.word.converter.WordInSentenceConverter;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.model.Words;
import pl.zgora.uz.wiea.pkdg.word.repository.WordInSentenceRepository;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static pl.zgora.uz.wiea.pkdg.word.converter.WordConverter.convertToEntity;
import static pl.zgora.uz.wiea.pkdg.word.converter.WordConverter.convertToModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    private final WordInSentenceRepository wordInSentenceRepository;

    @Transactional
    public Word createWord(Word word) {
        val wordEntity = convertToEntity(word);
        wordEntity.setWordId(UUID.randomUUID().toString());
        wordEntity.getExamples().forEach(e -> e.setWord(wordEntity));

        val savedWordEntity = wordRepository.save(wordEntity);

        log.debug("Word created with data={}", savedWordEntity);

        return convertToModel(savedWordEntity);
    }

    @Transactional
    public Words getWords(Pageable pageable) {
        val page = wordRepository.findAll(pageable);
        val wordList = page.getContent().stream().map(WordConverter::convertToModel).collect(toList());
        return new Words(wordList, page.getTotalElements());
    }

    @Transactional
    public Word getWordById(String wordId) {
        val wordEntity = wordRepository.findByWordId(wordId);
        return convertToModel(wordEntity);
    }

    @Transactional
    public List<String> getAutocomplete(String q) {
        return wordRepository.findDistinctEntriesLike(q);
    }

    @Transactional
    public List<Word> getWordsByEntry(String entry) {
        return wordRepository.findByEntry(entry).stream().map(WordConverter::convertToModel).collect(toList());
    }

    public List<Word> getAvailableWordsToRepeat(String username) {
        return wordRepository.findAvailableByUsername(username).stream().map(WordConverter::convertToModel).collect(toList());
    }

    @Transactional
    public Word updateWord(String wordId, Word word) {
        val wordEntity = wordRepository.findByWordId(wordId);
        if (wordEntity == null) {
            throw new WordNotFoundException(word.getId());
        }

        wordEntity.setEntry(word.getEntry());
        wordEntity.setDefinition(word.getDefinition());

        wordEntity.getExamples().clear();
        wordRepository.flush();

        val examples = word.getExamples().stream().map(WordInSentenceConverter::convertToEntity).collect(toSet());
        examples.forEach(e -> e.setWord(wordEntity));

        wordEntity.getExamples().addAll(examples);

        val updatedWord = wordRepository.save(wordEntity);

        log.debug("Word with wordId='{}' updated with data='{}'", wordId, updatedWord);

        return convertToModel(wordRepository.save(wordEntity));
    }

    @Transactional
    public void deleteWordById(String wordId) {
        wordRepository.deleteByWordId(wordId);
        log.debug("Word with wordId='{}' deleted", wordId);
    }
}
