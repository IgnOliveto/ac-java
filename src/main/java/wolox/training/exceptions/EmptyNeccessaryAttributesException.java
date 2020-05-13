package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Request body has empty fields.")
public class EmptyNeccessaryAttributesException extends RuntimeException {
    public EmptyNeccessaryAttributesException () {
        super("Request body has remaining fields to add.");
    }
}
