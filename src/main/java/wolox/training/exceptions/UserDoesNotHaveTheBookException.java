package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.models.Book;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User does not have the book.")
public class UserDoesNotHaveTheBookException extends RuntimeException {

    public UserDoesNotHaveTheBookException(Book book) {
        super("The user does not have the book with " + book.getId() + " and ISBN " + book.getIsbn());
    }
}
