package de.assecor.config.exception;

import org.springframework.http.HttpStatus;

public abstract class ServiceResponseException extends Exception {

    public ServiceResponseException(String message) {
        super(message);
    }

    public ServiceResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getHttpStatusCode();
}

