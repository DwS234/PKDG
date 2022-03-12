package pl.zgora.uz.wiea.pkdg.word.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.service.WordService;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @PostMapping("/words")
    public ResponseEntity<Word> createWord(@RequestBody Word word) {
        val createdWord = wordService.createWord(word);
        return new ResponseEntity<>(createdWord, HttpStatus.CREATED);
    }

    @GetMapping("/words")
    public ResponseEntity<List<Word>> getWords(Pageable pageable) {
        val words = wordService.getWords(pageable);
        return ResponseEntity.ok(words);
    }
}
