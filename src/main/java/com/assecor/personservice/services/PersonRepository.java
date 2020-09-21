package com.assecor.personservice.services;

import com.assecor.personservice.constant.ColorEntryEnum;
import com.assecor.personservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    List<Person> findByColor(ColorEntryEnum colorEntryEnum);

    boolean existsByNameAndLastName(String name, String lastName);
}