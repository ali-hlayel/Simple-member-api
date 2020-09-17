package de.assecor.person.controller;

import de.assecor.person.config.exception.BadRequestException;
import de.assecor.person.config.ImportCsvReader;
import de.assecor.person.config.exception.ServiceResponseException;
import de.assecor.person.person.PersonEntity;
import de.assecor.person.person.PersonRowModel;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


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
            PersonEntity person = mapImportModelToPersonDbo(personRowModel);
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

    @ApiOperation("Creates a Person")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonEntity createPerson( @RequestBody PersonRowModel createModel) throws ServiceResponseException {
        PersonEntity personEntity = new PersonEntity();
        BeanUtils.copyProperties(createModel, personEntity);
return personEntity;
        }

    @ApiOperation("get a Person")
    @GetMapping(path = "/{id}")
    public PersonRowModel getMember(@PathVariable long id) {
        PersonEntity person = personService.getOne(id);
        ModelMapper modelMapper = new ModelMapper();
        PersonRowModel returnValue = modelMapper.map(person, PersonRowModel.class);
        return returnValue;
    }
private PersonEntity mapImportModelToPersonDbo(PersonRowModel personRowModel) {

    PersonEntity person = new PersonEntity();
    BeanUtils.copyProperties(personRowModel, person);


    return person;
}
}
