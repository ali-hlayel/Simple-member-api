package de.assecor.config.helper;

import com.google.common.collect.Lists;
import de.assecor.person.PersonImportRowModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class CsvReader {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<PersonImportRowModel> csvToEntity(InputStream is) {
        List<PersonImportRowModel> personrows = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withSystemRecordSeparator()
                             .withDelimiter(',')
                             .withRecordSeparator("\r\n")
                             .withIgnoreEmptyLines(true)
                             .withTrailingDelimiter(true)
                             .withTrim());) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            List<String> lineValues = Lists.newArrayList();
            for (int i = 0; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
                for (Iterator<String> iterator = record.iterator(); iterator.hasNext(); ) {
                    lineValues.add(iterator.next());
                }
            }
            Iterator iterator = lineValues.iterator();
            while (iterator.hasNext()) {
                PersonImportRowModel personImportRowModel = new PersonImportRowModel();
                personImportRowModel.setName(iterator.next().toString().trim());
                personImportRowModel.setLastName(iterator.next().toString().trim());

                String[] address = iterator.next().toString().trim().split(" ");
                Long postZeil = Long.parseLong(address[0]);
                String city = address[1];
                personImportRowModel.setPostZeil(postZeil);
                personImportRowModel.setCity(city);
                personImportRowModel.setColor(Integer.valueOf(iterator.next().toString().trim()));
                System.out.println(personImportRowModel);
                personrows.add(personImportRowModel);
            }

        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
        return personrows;

    }



}
