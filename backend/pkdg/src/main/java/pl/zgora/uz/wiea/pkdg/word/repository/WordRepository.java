package pl.zgora.uz.wiea.pkdg.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {

    WordEntity findByWordId(String wordId);
}
