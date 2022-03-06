package pl.zgora.uz.wiea.pkdg.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
