package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User does not have books.")
public class UserDoesNotHaveAnyBookException extends RuntimeException {

    public UserDoesNotHaveAnyBookException(Long id) {
        super("The user with Id " + id + " does not have any book.");
    }
}
