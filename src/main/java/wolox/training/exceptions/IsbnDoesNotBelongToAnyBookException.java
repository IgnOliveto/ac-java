package wolox.training.exceptions;

public class IsbnDoesNotBelongToAnyBookException extends Exception{
    public IsbnDoesNotBelongToAnyBookException(String errorMessage) {
        super(errorMessage);
    }
}
