package pl.zgora.uz.wiea.pkdg.admin;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.zgora.uz.wiea.pkdg.word.model.Word;
import pl.zgora.uz.wiea.pkdg.word.model.Words;
import pl.zgora.uz.wiea.pkdg.word.service.WordService;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin-api")
@RequiredArgsConstructor
public class AdminWordController {

    private final WordService wordService;

    @PostMapping("/words")
    public ResponseEntity<Word> createWord(@RequestBody Word word) {
        val createdWord = wordService.createWord(word);
        return new ResponseEntity<>(createdWord, HttpStatus.CREATED);
    }

    @GetMapping("/words")
    public ResponseEntity<Words> getWords(Pageable pageable) {
        val words = wordService.getWords(pageable);
        return ResponseEntity.ok(words);
    }
}
