package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Body and params data do not match.")
public class BookIdMismatchException extends RuntimeException {
    public BookIdMismatchException(Long bookId, Long paramId) {
        super("The book ID " + bookId + " and the parameter id " + paramId + " do not match.");
    }
}
