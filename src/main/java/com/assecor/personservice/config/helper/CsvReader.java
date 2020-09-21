package com.assecor.personservice.config.helper;

import com.assecor.personservice.constant.ColorEntryEnum;
import com.assecor.personservice.model.PersonImportRowModel;
import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvReader {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    public static synchronized List<PersonImportRowModel> importCsvFileToPersonImportRowModel(InputStream inputStream) {
        List<PersonImportRowModel> personImportRowModels = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withSystemRecordSeparator()
                             .withDelimiter(',')
                             .withRecordSeparator("\r\n")
                             .withIgnoreEmptyLines(true)
                             .withTrailingDelimiter(true)
                             .withTrim())) {

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
                String[] address = iterator.next().toString().trim().split(" ", 2);
                Long cityCode = Long.parseLong(address[0]);
                String city = cityValidation(address[1]);
                personImportRowModel.setZipCode(cityCode);
                personImportRowModel.setCity(city);
                personImportRowModel.setColor(ColorEntryEnum.getByNumber(Integer.valueOf(iterator.next().toString().trim())));
                personImportRowModels.add(personImportRowModel);
            }

        } catch (IOException e) {
            throw new RuntimeException("fail to parse the CSV file: " + e.getMessage());
        }
        return personImportRowModels;
    }

    private static String cityValidation(String city) {
        String cityValidation = city.replaceAll("\\W", " ").trim();
        return cityValidation;
    }
}