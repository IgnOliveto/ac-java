package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.models.User;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists.")
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("Username " + username + " is already taken.");
    }
}
