package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class IsbnDoesNotBelongToAnyBookException extends RuntimeException{
    public IsbnDoesNotBelongToAnyBookException(String isbn) {
        super( "The book with ISBN " + isbn + " does not exist.");
    }
}
