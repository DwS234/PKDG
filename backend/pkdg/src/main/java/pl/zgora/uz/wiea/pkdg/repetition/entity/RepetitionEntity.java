package pl.zgora.uz.wiea.pkdg.repetition.entity;

import lombok.Data;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Table(name = "repetition")
@Entity
public class RepetitionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repetition_id")
	private Long id;

	private LocalDate nextDate;

	private double easiness;

	private int consecutiveCorrectAnswers;

	private int timesSeen;

	@Column(name = "last_interval")
	private int lastIntervalDays;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id")
	private WordEntity word;
}
