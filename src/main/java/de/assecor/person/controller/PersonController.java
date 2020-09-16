package de.assecor.person.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import de.assecor.person.service.PersonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import de.assecor.person.person.Person;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;


@Validated
@RestController
@RequestMapping(value = "persons", produces = "application/json")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ApiOperation("Gets a list of persons")

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<Person> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Person.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<Person> users = csvToBean.parse();

                // save users list on model
                model.addAttribute("users", users);
                model.addAttribute("status", true);

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }


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
}
