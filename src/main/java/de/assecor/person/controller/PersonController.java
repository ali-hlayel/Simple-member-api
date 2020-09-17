package de.assecor.person.controller;

import de.assecor.person.config.exception.BadRequestException;
import de.assecor.person.config.ImportCsvReader;
import de.assecor.person.config.exception.ServiceResponseException;
import de.assecor.person.person.Person;
import de.assecor.person.person.PersonDbo;
import de.assecor.person.person.PersonRowModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Validated
@RestController
@RequestMapping(value = "persons", produces = "application/json")
public class PersonController {

    private  final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ApiOperation("upload a list of persons from a csv file")
    @PostMapping("/upload-csv-file")
    public void importCsv(
            @RequestParam("file") MultipartFile multipartFile) throws IOException, ServiceResponseException {

        ImportCsvReader<PersonRowModel> csvReader = new ImportCsvReader<>(PersonRowModel.class);
        List<PersonRowModel> importRows = csvReader.importFromInputStream(multipartFile.getInputStream());
        if (importRows.isEmpty()) {
            throw new BadRequestException("The given CSV file is empty");
        }

        for(PersonRowModel personRowModel : importRows) {
            PersonDbo person = mapImportModelToPersonDbo(personRowModel);
            System.out.println(person.getCity());
            System.out.println(person.getId());
            personService.create(person);
/*
            Optional<Person> existingPrice = personService.getByRentalObjectId(personRowModel.getRentalObjectId());

            if (existingPrice.isPresent()) {
                priceService.update(existingPrice.get().getId(), price);
            } else {
                priceService.create(price);
            }*/
        }

    }
/*

    @ApiOperation("Gets a list of persons")
    @GetMapping
    public List<Person> getPersons(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<Person> result = Collections.emptyList();// personService.getPersons(page, limit);
        return result;
    }

    @ApiOperation("Gets a single person")
    @GetMapping(path = "/{id}")
    public Person getMember(@PathVariable long id) {
        Person result = null;//personService.getById(id);

        return result;
    }
    */
private PersonDbo mapImportModelToPersonDbo(PersonRowModel personRowModel) {

    PersonDbo person = new PersonDbo();
    BeanUtils.copyProperties(personRowModel, person);


    return person;
}
}
