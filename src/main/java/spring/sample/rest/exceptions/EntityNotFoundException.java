package spring.sample.rest.exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
