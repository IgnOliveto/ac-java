package wolox.training.exceptions;

public class IsbnDoesNotBelongToAnyBookException extends RuntimeException{
    public IsbnDoesNotBelongToAnyBookException(String isbn) {
        super( "The book with ISBN " + isbn + " does not exist.");
    }
}
