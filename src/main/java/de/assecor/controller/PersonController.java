package de.assecor.controller;

import de.assecor.config.exception.CreateErrorException;
import de.assecor.config.exception.ServiceResponseException;
import de.assecor.config.helper.CsvReader;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.Person;
import de.assecor.person.PersonCreateModel;
import de.assecor.person.PersonModel;
import de.assecor.services.PersonService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ApiOperation("upload a list of persons from a csv file")
    @PostMapping("/upload-csv-file")
    public void importCsvFile(
            @RequestParam("file") MultipartFile file) {

        if (CsvReader.hasCSVFormat(file)) {
            try {
                personService.Upload(file);

            } catch (Exception e) {
                String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            }
        }
    }

    @ApiOperation("Creates new Person")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@Valid @RequestBody PersonCreateModel createModel) throws CreateErrorException {
        Person person = new Person();
        BeanUtils.copyProperties(createModel, person);
        Person result = personService.createPerson(person);
        return result;
    }

    @ApiOperation("Get all persons")
    @GetMapping()
    public ResponseEntity<List<PersonModel>> getPersons(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "2") int limit) {
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

    @ApiOperation("Get Person by id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonModel> gePerson(@Min(1) @PathVariable long id) throws ServiceResponseException {
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
    @GetMapping(path = "/color/{color}")
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
