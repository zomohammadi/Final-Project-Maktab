package exception;

public class IoCustomException extends RuntimeException {
    public IoCustomException() {
        super();
    }

    public IoCustomException(String message) {
        super(message);
    }

    public IoCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
