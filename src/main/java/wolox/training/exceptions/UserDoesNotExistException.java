package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Non existent user.")
public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException(Long id) {
        super("User " + id + " does not exist.");
    }

    public UserDoesNotExistException(String userName) {
        super("User " + userName + " does not exist.");
    }
}
