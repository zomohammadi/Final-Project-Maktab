package exception;

public class FoundException extends RuntimeException {
    public FoundException() {
        super();
    }

    public FoundException(String message) {
        super(message);
    }

    public FoundException(String message, Throwable cause) {
        super(message, cause);
    }
}