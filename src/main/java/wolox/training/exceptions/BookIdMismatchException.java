package wolox.training.exceptions;

public class BookIdMismatchException extends RuntimeException {
    public BookIdMismatchException(Long bookId, Long paramId) {
        super("The book ID " + bookId + " and the parameter id " + paramId + " do not match.");
    }
}
