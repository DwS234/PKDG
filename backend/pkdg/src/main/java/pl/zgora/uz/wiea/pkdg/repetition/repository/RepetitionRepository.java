package pl.zgora.uz.wiea.pkdg.repetition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;

import java.util.List;

@Repository
public interface RepetitionRepository extends JpaRepository<RepetitionEntity, Long> {

    @Query("SELECT r from RepetitionEntity r WHERE r.user.username = :username")
    List<RepetitionEntity> findAllByUsername(@Param("username") String username);

    @Query("SELECT r from RepetitionEntity r WHERE r.user.username = :username and r.repetitionId = :repetitionId")
    RepetitionEntity findByUsernameAndRepetitionId(@Param("username") String username, @Param("repetitionId") String repetitionId);
}
