package pl.zgora.uz.wiea.pkdg.word.entity;

import lombok.Data;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@Table(name = "words")
@Entity
public class WordEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "word_id")
	private Long id;

	private String entry;

	private String definition;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "word", orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<WordInSentenceEntity> examples;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "word", orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<RepetitionEntity> repetitions;
}
