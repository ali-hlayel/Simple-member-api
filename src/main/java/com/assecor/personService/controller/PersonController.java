package com.assecor.personService.controller;

import com.assecor.personService.config.exception.ConflictException;
import com.assecor.personService.config.exception.EntityAlreadyExistsException;
import com.assecor.personService.config.exception.NotFoundException;
import com.assecor.personService.config.exception.ServiceResponseException;
import com.assecor.personService.config.helper.CsvReader;
import com.assecor.personService.constant.ColorEntryEnum;
import com.assecor.personService.entity.Person;
import com.assecor.personService.model.PersonCreateModel;
import com.assecor.personService.model.PersonImportRowModel;
import com.assecor.personService.model.PersonModel;
import com.assecor.personService.services.PersonService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ApiOperation("Get all persons")
    @GetMapping(value = "/persons")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonModel> getPersons(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit) throws ServiceResponseException {
        List<PersonModel> results = new ArrayList<>();
        try {
            List<Person> personsList = personService.getPersons(page, limit);
            ModelMapper modelMapper = new ModelMapper();
            for (Person person : personsList) {
                results.add(modelMapper.map(person, PersonModel.class));
            }
        } catch (NoResultException e) {
            String message = "Could not create person: " + e.getMessage();
            LOGGER.error(message, e);
            throw new NotFoundException(message, e);
        }
        return results;
    }

    @ApiOperation("upload a list of persons from a csv file")
    @PostMapping("/import")
    public List<Person> importCsvFile(
            @RequestParam("file") MultipartFile file) throws ServiceResponseException {
        List<Person> personList = new ArrayList<>();
        if (CsvReader.hasCSVFormat(file)) {
            try {
                List<PersonImportRowModel> personEntities = CsvReader.importCsvFileToPersonImportRowModel(file.getInputStream());
                for (PersonImportRowModel importedPerson : personEntities) {
                    Person person = new Person();
                    BeanUtils.copyProperties(importedPerson, person);
                    personList.add(person);
                    try {
                        personService.createPerson(person);
                    } catch (EntityAlreadyExistsException e) {
                        String message = "Could not create person: " + e.getMessage();
                        LOGGER.error(message, e);
                        throw new ConflictException(message, e);
                    }
                }
            } catch (IOException e) {
                String message = "fail to store csv data: " + e.getMessage();
                LOGGER.error(message, e);
                throw new RuntimeException(message + e.getMessage());
            }
        }
        return personList;
    }

    @ApiOperation("Creates new Person")
    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@Valid @RequestBody PersonCreateModel createModel) throws ServiceResponseException {
        Person person = new Person();
        BeanUtils.copyProperties(createModel, person);
        Person result;
        try {
            result = personService.createPerson(person);
        } catch (EntityAlreadyExistsException e) {
            String message = "Could not create person: " + e.getMessage();
            LOGGER.error(message, e);
            throw new ConflictException(message, e);
        }
        return result;

    }

    @ApiOperation("Get Person by id")
    @GetMapping(path = "/person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonModel gePerson(@Min(1) @PathVariable long id) throws ServiceResponseException {
        Person result;
        PersonModel person;
        try {
            result = personService.getById(id);
            ModelMapper modelMapper = new ModelMapper();
            person = modelMapper.map(result, PersonModel.class);
        } catch (NoResultException e) {
            String message = "Could not find a person with id " + id + ": " + e.getMessage();
            LOGGER.error(message, e);
            throw new NotFoundException(message, e);
        }
        return person;
    }

    @ApiOperation("Get by colors")
    @GetMapping(path = "/person/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonModel> getPersonByColor(@PathVariable String color) throws ServiceResponseException {
        List<PersonModel> results = new ArrayList<>();
        List<Person> personsList;
        ModelMapper modelMapper = new ModelMapper();
        try {
            personsList = personService.getByColor(ColorEntryEnum.valueOf(color));
            for (Person person : personsList) {
                results.add(modelMapper.map(person, PersonModel.class));
            }
        } catch (NoResultException e) {
            String message = "Could not find a person with color " + color + ": " + e.getMessage();
            LOGGER.error(message, e);
            throw new NotFoundException(message, e);
        }
        return results;
    }
}
