package pl.zgora.uz.wiea.pkdg.word.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.zgora.uz.wiea.pkdg.word.converter.WordConverter;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.repository.WordRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    public List<Word> getWords() {
        val wordEntities = wordRepository.findAll();
        return wordEntities.stream().map(WordConverter::convertToModel).collect(toList());
    }
}
