package wolox.training.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.AuthorDoesNotHaveBooksException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.exceptions.*;

@RestController
@RequestMapping("api/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public List findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/author/{bookAuthor}")
    public Book findOneByAuthor(@PathVariable String bookAuthor) {
            return bookRepository.findOneByAuthor(bookAuthor)
                .orElseThrow(AuthorDoesNotHaveBooksException::new);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        try {
            return bookRepository.findById(id);
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/isbn/{isbn}")
    public Book findOne(@PathVariable String isbn) {
        try {
            return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IsbnDoesNotBelongToAnyBookException("The isbn is not correct."));
        } catch (IsbnDoesNotBelongToAnyBookException e) {
            e.printStackTrace();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book does not exist."));
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
        bookRepository.deleteById(id);
    }

    @DeleteMapping("/author/{author}")
    public void deleteAllByAuthor(@PathVariable String author) {
        try {
            bookRepository.findOneByAuthor(author)
                .orElseThrow(() -> new AuthorDoesNotHaveBooksException("Author does not have any book."));
        } catch (AuthorDoesNotHaveBooksException error) {
            error.printStackTrace();
        }
        bookRepository.deleteByAuthor(author);
    }

    @DeleteMapping("/publisher/{publisher}")
    public void deleteAllByPublisher(@PathVariable String publisher) {
        try {
            bookRepository.findOneByPublisher(publisher)
                .orElseThrow(() -> new PublisherDoesNotHaveBooksException("Publisher has not published any book."));
        } catch (PublisherDoesNotHaveBooksException error) {
            error.printStackTrace();
        }
        bookRepository.deleteByPublisher(publisher);
    }

    @DeleteMapping("/isbn/{isbn}")
    public void deleteByIsbn(@PathVariable String isbn) {
        try {
            bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IsbnDoesNotBelongToAnyBookException("The isbn is not correct."));
        } catch (IsbnDoesNotBelongToAnyBookException error) {
            error.printStackTrace();
        }
        bookRepository.deleteByIsbn(isbn);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        try {
            if (book.getId() != id) {
                throw new BookIdMismatchException("The input book Id does not match with params Id.");
            }
            bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book does not exist."));
            return bookRepository.save(book);
        } catch (BookIdMismatchException error) {
            error.printStackTrace();
        } catch (BookNotFoundException error) {
            error.printStackTrace();
        }
    }
}
