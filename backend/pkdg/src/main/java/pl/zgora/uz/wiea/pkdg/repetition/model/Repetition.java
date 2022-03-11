package pl.zgora.uz.wiea.pkdg.repetition.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Repetition {

    private String repetitionId;

    private LocalDate nextDate;

    private double easiness;

    private int consecutiveCorrectAnswers;

    private int timesSeen;
}
