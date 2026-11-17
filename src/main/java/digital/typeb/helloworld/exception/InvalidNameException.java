package digital.typeb.helloworld.exception;

/**
 * Thrown when the provided name parameter is missing, blank, or starts with an invalid character.
 */
public class InvalidNameException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid Input";

    public InvalidNameException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidNameException(String message) {
        super(message);
    }
}
