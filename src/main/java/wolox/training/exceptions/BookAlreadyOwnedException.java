package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.models.Book;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The user already has that book.")
public class BookAlreadyOwnedException extends RuntimeException {
    public BookAlreadyOwnedException (String username, Book book) {
        super("The user " + username + "already has the book " + book.getTitle() + " with ID " + book.getId() + ".");
    }
}
