package pl.zgora.uz.wiea.pkdg.user.entity;

import lombok.Data;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@Table(name = "users")
@Entity
public class UserEntity {

	@Id
	private String username;

	private String password;

	private String email;

	private short enabled;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<RepetitionEntity> repetitions;
}
