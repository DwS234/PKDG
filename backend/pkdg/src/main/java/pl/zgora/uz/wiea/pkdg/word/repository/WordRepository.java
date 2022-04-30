package pl.zgora.uz.wiea.pkdg.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {

    WordEntity findByWordId(String wordId);

    @Query("SELECT DISTINCT w.entry FROM WordEntity w WHERE w.entry LIKE :q%")
    List<String> findDistinctEntriesLike(String q);

    List<WordEntity> findByEntry(String entry);

    @Query("SELECT DISTINCT(w) FROM WordEntity w LEFT JOIN FETCH w.examples e LEFT JOIN w.repetitions r WITH r.word.id = w.id AND r.user.username = :username WHERE r.word.id IS NULL ORDER BY w.entry")
    List<WordEntity> findAvailableByUsername(String username);

    long deleteByWordId(String wordId);
}
