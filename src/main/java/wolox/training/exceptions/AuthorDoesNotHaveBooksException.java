package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AuthorDoesNotHaveBooksException extends RuntimeException {
    public AuthorDoesNotHaveBooksException(String author) {
        super("Author " + author + " does not have any book.");
    }
}
