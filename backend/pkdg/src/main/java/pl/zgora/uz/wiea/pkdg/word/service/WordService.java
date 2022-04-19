package pl.zgora.uz.wiea.pkdg.word.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.zgora.uz.wiea.pkdg.word.converter.WordConverter;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.model.Words;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static pl.zgora.uz.wiea.pkdg.word.converter.WordConverter.convertToEntity;
import static pl.zgora.uz.wiea.pkdg.word.converter.WordConverter.convertToModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

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
}
