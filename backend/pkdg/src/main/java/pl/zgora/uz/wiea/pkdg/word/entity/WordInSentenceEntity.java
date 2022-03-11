package pl.zgora.uz.wiea.pkdg.word.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "word_in_sentences")
@Entity
public class WordInSentenceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String sentence;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id", nullable = false)
	private WordEntity word;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof WordInSentenceEntity)) return false;
		return id != null && id.equals(((WordInSentenceEntity) o).getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
