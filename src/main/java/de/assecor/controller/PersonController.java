package de.assecor.controller;

import de.assecor.config.exception.CreateErrorException;
import de.assecor.config.helper.CsvReader;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.PersonEntity;
import de.assecor.person.PersonModel;
import de.assecor.services.PersonService;
import io.swagger.annotations.ApiOperation;
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
    public PersonEntity createPerson(@Valid @RequestBody PersonModel createModel) throws CreateErrorException {
        PersonEntity personEntity = mapImportModelToPersonDbo(createModel);
        PersonEntity result = personService.createPerson(personEntity);
        return result;
    }

    @ApiOperation("Get all persons")
    @GetMapping()
    public List<PersonEntity> getPersons() {

        return personService.get();
    }

    @ApiOperation("Get Person by id")
    @GetMapping(path = "/{id}")
    public PersonEntity getMember(@Min (1) @PathVariable long id) throws NoResultException {
        PersonEntity person = personService.getById(id);
        return person;
    }

    @ApiOperation("Get by colors")
    @GetMapping(path = "/color/{color}")
    public List<PersonEntity> getPersonByColor(@PathVariable String color) {

         return personService.getByColor(ColorEntryEnum.valueOf(color));
    }

    private PersonEntity mapImportModelToPersonDbo(PersonModel personModel) {
        PersonEntity person = new PersonEntity();
        BeanUtils.copyProperties(personModel, person);

        return person;
    }
}
