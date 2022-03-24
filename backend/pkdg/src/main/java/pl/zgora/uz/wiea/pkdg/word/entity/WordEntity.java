package pl.zgora.uz.wiea.pkdg.word.entity;

import lombok.Data;
import lombok.ToString;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "words")
@Entity
@ToString(exclude = {"examples", "repetitions"})
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wordId;

    private String entry;

    private String definition;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "word", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<WordInSentenceEntity> examples = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "word", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RepetitionEntity> repetitions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordEntity)) return false;
        return id != null && id.equals(((WordEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
