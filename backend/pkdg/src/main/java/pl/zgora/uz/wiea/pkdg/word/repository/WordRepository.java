package pl.zgora.uz.wiea.pkdg.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zgora.uz.wiea.pkdg.word.entity.WordEntity;

interface WordRepository extends JpaRepository<WordEntity, Long> {

}
