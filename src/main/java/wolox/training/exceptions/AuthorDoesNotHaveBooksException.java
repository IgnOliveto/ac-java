package wolox.training.exceptions;

public class AuthorDoesNotHaveBooksException extends Exception {
    public AuthorDoesNotHaveBooksException() {
        super("Author does not have any book.");
    }
}
