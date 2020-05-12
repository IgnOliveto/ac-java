package wolox.training.exceptions;

public class PublisherDoesNotHaveBooksException extends Exception {
    public PublisherDoesNotHaveBooksException(String errorMessage) {
        super(errorMessage);
    }
}
