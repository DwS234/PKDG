package pl.zgora.uz.wiea.pkdg.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zgora.uz.wiea.pkdg.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String username);
}
