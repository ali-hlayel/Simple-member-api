package com.assecor.personService.utils.exception;

public class EntityAlreadyExistsException extends Exception {

    private Object existingEntity;

    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(Object existingEntity, String message) {
        super(message);

        this.existingEntity = existingEntity;
    }

    public Object getExistingEntity() {
        return existingEntity;
    }
}
