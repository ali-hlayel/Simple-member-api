package de.assecor.services;

import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    List<PersonEntity> findByColor(ColorEntryEnum colorEntryEnum);
}