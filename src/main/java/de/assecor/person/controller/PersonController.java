package de.assecor.person.controller;

import de.assecor.person.config.helper.CsvReader;
import de.assecor.person.person.ColourEntryEnum;
import de.assecor.person.person.PersonEntity;
import de.assecor.person.person.PersonRowModel;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping(value = "persons", produces = "application/json")
public class PersonController {

    @Autowired
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ApiOperation("upload a list of persons from a csv file")
    @PostMapping("/upload-csv-file")
    public void importCsvFile(
            @RequestParam("file") MultipartFile file) {

        if (CsvReader.hasCSVFormat(file)) {
            try {
                personService.save(file);

            } catch (Exception e) {
              String  message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            }
        }
    }

    @ApiOperation("Creates a Person")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonEntity createPerson(@RequestBody PersonRowModel createModel) {
        PersonEntity personEntity = mapImportModelToPersonDbo(createModel);
        PersonEntity result = personService.create(personEntity);
        return result;
    }

    @GetMapping()
    public List<PersonEntity> getPersons() {

        return personService.get();
    }

    @ApiOperation("get a Person")
    @GetMapping(path = "/{id}")
    public PersonRowModel getMember(@PathVariable long id) {
        PersonEntity person = personService.getOne(id);
        ModelMapper modelMapper = new ModelMapper();
        PersonRowModel returnValue = modelMapper.map(person, PersonRowModel.class);
        return returnValue;
    }

    @ApiOperation("Get by colors")
    @GetMapping(path = "/color/{color}")
    public List<PersonEntity> getPersonByColor(@PathVariable String color) {
        return personService.getByColor(ColourEntryEnum.valueOf(color).getNumber());
    }

    private PersonEntity mapImportModelToPersonDbo(PersonRowModel personRowModel) {
        PersonEntity person = new PersonEntity();
        BeanUtils.copyProperties(personRowModel, person);

        return person;
    }
}
