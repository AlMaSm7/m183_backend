package ch.bbw.pwd_manager.exceptions;

public class InvalidOperation extends RuntimeException {
    public InvalidOperation(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperation(String message) {
        super(message);
    }
}
