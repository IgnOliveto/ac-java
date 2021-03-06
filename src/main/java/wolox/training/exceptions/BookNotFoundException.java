package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Book not found for ID.")
public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long id) {
        super("The book with ID " + id + " does not exist");
    }
}
