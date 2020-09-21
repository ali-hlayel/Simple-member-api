package com.assecor.personservice.controller;

import com.assecor.personservice.config.exception.EntityAlreadyExistsException;
import com.assecor.personservice.config.helper.CsvReader;
import com.assecor.personservice.constant.ColorEntryEnum;
import com.assecor.personservice.entity.Person;
import com.assecor.personservice.model.PersonCreateModel;
import com.assecor.personservice.model.PersonImportRowModel;
import com.assecor.personservice.model.PersonModel;
import com.assecor.personservice.services.PersonService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/person-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ApiOperation("Get all persons")
    @GetMapping(value = "/persons")
    public ResponseEntity<List<PersonModel>> getPersons(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "10") int limit) {
        try {
            List<PersonModel> results = new ArrayList<>();
            List<Person> personsList = personService.getPersons(page, limit);
            ModelMapper modelMapper = new ModelMapper();
            for (Person person : personsList) {
                results.add(modelMapper.map(person, PersonModel.class));
            }
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation("upload a list of persons from a csv file")
    @PostMapping("/import")
    public List<Person> importCsvFile(
            @RequestParam("file") MultipartFile file) {
        List<Person> personList = new ArrayList<>();
        if (CsvReader.hasCSVFormat(file)) {
            try {
                List<PersonImportRowModel> personEntities = CsvReader.importCsvFileToPersonImportRowModel(file.getInputStream());
                for (PersonImportRowModel importedPerson : personEntities) {
                    Person person = new Person();
                    BeanUtils.copyProperties(importedPerson, person);
                    personList.add(person);
                    personService.createPerson(person);
                }
            } catch (IOException | EntityAlreadyExistsException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }
        return personList;
    }

    @ApiOperation("Creates new Person")
    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@Valid @RequestBody PersonCreateModel createModel) throws EntityAlreadyExistsException {
        Person person = new Person();
        BeanUtils.copyProperties(createModel, person);
        Person result = personService.createPerson(person);
        return result;
    }

    @ApiOperation("Get Person by id")
    @GetMapping(path = "/person/{id}")
    public ResponseEntity<PersonModel> gePerson(@Min(1) @PathVariable long id) {
        Person result;
        PersonModel person;
        try {
            result = personService.getById(id);
            ModelMapper modelMapper = new ModelMapper();
            person = modelMapper.map(result, PersonModel.class);

            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation("Get by colors")
    @GetMapping(path = "/person/color/{color}")
    public ResponseEntity<List<PersonModel>> getPersonByColor(@PathVariable String color) {
        try {
            List<PersonModel> results = new ArrayList<>();
            List<Person> personsList = personService.getByColor(ColorEntryEnum.valueOf(color));
            ModelMapper modelMapper = new ModelMapper();
            for (Person person : personsList) {
                results.add(modelMapper.map(person, PersonModel.class));
            }
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
