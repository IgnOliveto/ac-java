package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User could not be created.")
public class FailedToCreateUserException extends RuntimeException {

    public FailedToCreateUserException(Exception e) {
        super(e.getMessage());
    }
}
