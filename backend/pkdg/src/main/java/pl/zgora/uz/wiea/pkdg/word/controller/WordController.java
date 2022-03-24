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

    @GetMapping("/words/autocomplete")
    public ResponseEntity<List<String>> getWordsAutocomplete(@RequestParam("q") String q) {
        val autocomplete = wordService.getAutocomplete(q);
        return ResponseEntity.ok(autocomplete);
    }

    @GetMapping("/words/entry/{entry}")
    public ResponseEntity<List<Word>> getWordsByEntry(@PathVariable String entry) {
        val words = wordService.getWordsByEntry(entry);
        return ResponseEntity.ok(words);
    }

    @GetMapping("/users/{username}/words/available-words-to-repeat")
    public ResponseEntity<List<Word>> getAvailableWordsToRepeat(@PathVariable String username) {
        val words = wordService.getAvailableWordsToRepeat(username);
        return ResponseEntity.ok(words);
    }
}
