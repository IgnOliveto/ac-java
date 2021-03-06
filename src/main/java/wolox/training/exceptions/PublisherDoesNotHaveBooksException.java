package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Book not found for the publisher.")
public class PublisherDoesNotHaveBooksException extends RuntimeException {
    public PublisherDoesNotHaveBooksException(String publisher) {
        super("Publisher " + publisher + " does not have any book.");
    }
}
