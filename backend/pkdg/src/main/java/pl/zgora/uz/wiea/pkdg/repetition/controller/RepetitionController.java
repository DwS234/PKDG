package pl.zgora.uz.wiea.pkdg.repetition.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zgora.uz.wiea.pkdg.repetition.model.Repetition;
import pl.zgora.uz.wiea.pkdg.repetition.service.RepetitionService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RepetitionController {

    private final RepetitionService repetitionService;

    @PostMapping("/users/{username}/words/{wordId}/repetitions")
    public ResponseEntity<Repetition> createRepetition(@PathVariable String username, @PathVariable String wordId, @RequestBody Repetition repetition) {
        val addedRepetition = repetitionService.createRepetition(username, wordId, repetition);
        return new ResponseEntity<>(addedRepetition, HttpStatus.CREATED);
    }

    @GetMapping("/users/{username}/repetitions")
    public ResponseEntity<List<Repetition>> getRepetitionsByUsername(@PathVariable String username) {
        val repetitions = repetitionService.getRepetitionsByUsername(username);
        return ResponseEntity.ok(repetitions);
    }

    @PutMapping("/repetitions/{repetitionId}")
    public ResponseEntity<Repetition> updateRepetition(@PathVariable String repetitionId, @RequestBody Repetition repetition) {
        val updatedRepetition = repetitionService.updateRepetition(repetitionId, repetition);
        return ResponseEntity.ok(updatedRepetition);
    }
}
