package wolox.training.exceptions;

public class PublisherDoesNotHaveBooksException extends RuntimeException {
    public PublisherDoesNotHaveBooksException(String publisher) {
        super("Publisher " + publisher + " does not have any book.");
    }
}
