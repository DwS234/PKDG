package pl.zgora.uz.wiea.pkdg.repetition.entity;

import lombok.Data;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = "repetitions")
@Entity
public class RepetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String repetitionId;

    private LocalDate nextDate;

    private double easiness;

    private int consecutiveCorrectAnswers;

    private int timesSeen;

    @Column(name = "last_interval")
    private int lastIntervalDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private WordEntity word;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepetitionEntity)) return false;
        return id != null && id.equals(((RepetitionEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
