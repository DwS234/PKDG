package pl.zgora.uz.wiea.pkdg.repetition.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zgora.uz.wiea.pkdg.repetition.model.Repetition;
import pl.zgora.uz.wiea.pkdg.repetition.service.RepetitionService;

import java.util.List;

@RestController
@RequestMapping("/api/repetitions")
@RequiredArgsConstructor
public class RepetitionController {

    private final RepetitionService repetitionService;

    @GetMapping("/{username}")
    public ResponseEntity<List<Repetition>> getRepetitionsByUsername(@PathVariable String username) {
        val repetitions = repetitionService.getRepetitionsByUsername(username);
        return ResponseEntity.ok(repetitions);
    }

    @PutMapping("/{username}/{repetitionId}")
    public ResponseEntity<Repetition> updateRepetition(@PathVariable String username, @PathVariable String repetitionId, @RequestBody Repetition repetition) {
        val updatedRepetition = repetitionService.updateRepetition(username, repetitionId, repetition);
        return ResponseEntity.ok(updatedRepetition);
    }
}
