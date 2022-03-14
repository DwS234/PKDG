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
}
