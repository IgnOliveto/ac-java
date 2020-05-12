package wolox.training.exceptions;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long id) {
        super("The book with ID " + id + " does not exist");
    }
}
