package de.assecor.person.config;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvException;
import de.assecor.person.config.exception.CreateErrorException;
import de.assecor.person.config.exception.ImportException;
import de.assecor.person.config.exception.ServiceResponseException;

import java.beans.PropertyEditorManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class ImportCsvReader<T> {

    private CsvToBean<T> csvToBean;

    public ImportCsvReader(Class<T> type) {

        MappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(type);

        csvToBean = new CsvToBean<>();
        csvToBean.setErrorLocale(Locale.ENGLISH);
        csvToBean.setMappingStrategy(mappingStrategy);
        csvToBean.setOrderedResults(true);
        csvToBean.setThrowExceptions(false);
    }

    public List<T> importFromInputStream(InputStream inputStream) throws ServiceResponseException {

        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
        csvToBean.setCsvReader(csvReader);

        List<T> importRows;
        try {
            importRows = csvToBean.parse();
        } catch(Exception exception) {
            // This is necessary because "setThrowExceptions" only pertains to CsvExceptions
            String message = exception.getCause() == null ? exception.getMessage() : exception.getCause().getMessage();

            // We can't throw an ImportException because we don't know the line number etc. from the failure. :(
            throw new CreateErrorException("Could not import: " + message, exception);
        }


        List<CsvException> readExceptions = csvToBean.getCapturedExceptions();
        if (!readExceptions.isEmpty()) {
            throw new ImportException("Could not import: " + readExceptions.size() + " failing CSV rows", readExceptions);
        }
        
        return importRows;
    }
}
