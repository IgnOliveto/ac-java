package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Book could not be deleted.")
public class FailedToDeleteBookException extends RuntimeException {

    public FailedToDeleteBookException(Exception e) {
        super(e.getMessage());
    }
}
