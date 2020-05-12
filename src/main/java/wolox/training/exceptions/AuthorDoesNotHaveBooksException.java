package wolox.training.exceptions;

public class AuthorDoesNotHaveBooksException extends RuntimeException {
    public AuthorDoesNotHaveBooksException(String author) {
        super("Author " + author + " does not have any book.");
    }
}
