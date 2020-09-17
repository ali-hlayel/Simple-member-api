package de.assecor.person.config.exception;

import com.opencsv.exceptions.CsvException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ImportException extends ServiceResponseException {

    private List<CsvException> exceptions;

    public ImportException(String message, List<CsvException> exceptionList) {
        super(message, exceptionList.get(0));

        exceptions = exceptionList;
    }

    public List<CsvException> getExceptions() {
        return exceptions;
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
