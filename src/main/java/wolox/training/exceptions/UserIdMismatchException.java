package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Body and params data do not match.")
public class UserIdMismatchException extends RuntimeException {

    public UserIdMismatchException(Long userId, Long paramId) {
        super("The user ID " + userId + " and the parameter id " + paramId + " do not match.");
    }
}
