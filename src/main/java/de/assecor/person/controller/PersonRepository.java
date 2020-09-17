package de.assecor.person.controller;

import de.assecor.person.person.PersonEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface PersonRepository extends PagingAndSortingRepository<PersonEntity, Long>, JpaSpecificationExecutor<PersonEntity> {
}