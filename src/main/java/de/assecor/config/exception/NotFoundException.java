package de.assecor.config.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceResponseException {

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
