package pl.zgora.uz.wiea.pkdg.repetition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;
import pl.zgora.uz.wiea.pkdg.repetition.model.WordInRepetition;

import java.util.List;

@Repository
public interface RepetitionRepository extends JpaRepository<RepetitionEntity, Long> {

    @Query("SELECT r from RepetitionEntity r JOIN FETCH r.word WHERE r.user.username = :username")
    List<RepetitionEntity> findAllByUsername(@Param("username") String username);

    RepetitionEntity findByRepetitionId(String repetitionId);

    @Query("SELECT new pl.zgora.uz.wiea.pkdg.repetition.model.WordInRepetition(r.word.wordId, r.repetitionId) FROM RepetitionEntity r WHERE r.word.wordId IN (:wordsIds) AND r.user.username = :username")
    List<WordInRepetition> findAllByWordsIdsAndUsername(@Param("wordsIds") List<String> wordsIds, @Param("username") String username);
}
