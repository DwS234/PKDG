package pl.zgora.uz.wiea.pkdg.repetition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zgora.uz.wiea.pkdg.repetition.entity.RepetitionEntity;

interface RepetitionRepository extends JpaRepository<RepetitionEntity, Long> {

}
