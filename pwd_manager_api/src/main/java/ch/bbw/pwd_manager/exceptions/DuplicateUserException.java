package ch.bbw.pwd_manager.exceptions;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUserException(String message) {
        super(message);
    }
}
