package de.assecor.controller;

import de.assecor.config.exception.CreateErrorException;
import de.assecor.config.exception.NotFoundException;
import de.assecor.config.exception.ServiceResponseException;
import de.assecor.config.helper.CsvReader;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.Person;
import de.assecor.person.PersonModel;
import de.assecor.services.PersonService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping(value = "persons", produces = "application/json")
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
                System.out.println( "----------------");

                personService.Upload(file);

            } catch (Exception e) {
              String  message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            }
        }
    }

    @ApiOperation("Creates new Person")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@Valid @RequestBody PersonModel createModel) throws CreateErrorException {
        Person person = new Person();
        BeanUtils.copyProperties(createModel, person);
        Person result = personService.createPerson(person);
        return result;
    }

    @ApiOperation("Get all persons")
    @GetMapping()
    public List<Person> getPersons() {

        return personService.get();
    }

    @ApiOperation("Get Person by id")
    @GetMapping(path = "/{id}")
    public Person gePerson(@Min (1) @PathVariable long id) throws ServiceResponseException {
        Person result;
        try {
            result = personService.getById(id);
        } catch (NoResultException e) {
            String message = "Could not find person with id " + id + ": " + e.getMessage();
            LOGGER.error(message, e);
            throw new NotFoundException(message, e);
        }
        return result;
    }

    @ApiOperation("Get by colors")
    @GetMapping(path = "/color/{color}")
    public List<Person> getPersonByColor(@PathVariable String color) {

         return personService.getByColor(ColorEntryEnum.valueOf(color));
    }
}
