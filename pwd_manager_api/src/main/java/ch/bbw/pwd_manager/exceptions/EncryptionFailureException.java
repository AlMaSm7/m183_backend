package ch.bbw.pwd_manager.exceptions;

public class EncryptionFailureException extends RuntimeException {

    public EncryptionFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptionFailureException(String message) {
        super(message);
    }
}
