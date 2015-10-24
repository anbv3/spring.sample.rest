package spring.sample.rest.exceptions;

public class DuplicatedEntityException extends Exception {

    public DuplicatedEntityException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

    public DuplicatedEntityException(String errorMessage) {
        super(errorMessage);
    }

}
