package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Book could not be created.")
public class FailedToCreateBookException extends RuntimeException {

    public FailedToCreateBookException(Exception e) {
        super(e.getMessage());
    }
}
