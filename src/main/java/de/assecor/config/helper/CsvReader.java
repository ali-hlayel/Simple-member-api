package de.assecor.config.helper;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import de.assecor.person.PersonImportRowModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvReader {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<PersonImportRowModel> csvToTutorials(Reader is) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(String.valueOf(new BufferedReader(is))));

             List<PersonImportRowModel> personImportRowModels = new ArrayList<PersonImportRowModel>();

             List<String[]> records = reader.readAll();

             Iterator<String[]> iterator = records.iterator();
        System.out.println(iterator + "----------------");

		while (iterator.hasNext()) {
            String[] record = iterator.next();
            PersonImportRowModel personImportRowModel = new PersonImportRowModel();
            personImportRowModel.setName(record[0]);
            personImportRowModel.setLastName(record[1]);
            personImportRowModel.setAddress(record[2]);
            personImportRowModel.setColor(record[3]);
            personImportRowModels.add(personImportRowModel);
            System.out.println(personImportRowModel.getAddress() + "----------------");

        }


        reader.close();
return personImportRowModels;
    }

}