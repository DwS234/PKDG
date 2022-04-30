package pl.zgora.uz.wiea.pkdg.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zgora.uz.wiea.pkdg.word.entity.WordInSentenceEntity;

@Repository
public interface WordInSentenceRepository extends JpaRepository<WordInSentenceEntity, Long> {

}
