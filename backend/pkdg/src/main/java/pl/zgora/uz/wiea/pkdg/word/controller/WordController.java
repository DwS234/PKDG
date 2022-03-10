package pl.zgora.uz.wiea.pkdg.word.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.service.WordService;

import java.util.List;

@RequestMapping("/api/words")
@RestController
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping("/")
    public ResponseEntity<List<Word>> getWords() {
        val words = wordService.getWords();
        return ResponseEntity.ok(words);
    }
}
