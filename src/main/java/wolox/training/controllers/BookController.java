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
    public List findByTitle(@PathVariable final String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/author/{bookAuthor}")
    public Book findOneByAuthor(@PathVariable final String bookAuthor) {
            return bookRepository.findOneByAuthor(bookAuthor)
                .orElseThrow(() -> new AuthorDoesNotHaveBooksException(bookAuthor));
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable final Long id) {
            return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @GetMapping("/isbn/{isbn}")
    public Book findOne(@PathVariable final String isbn) {
        return bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new IsbnDoesNotBelongToAnyBookException(isbn));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody final Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final Long id) {
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.deleteById(id);
    }

    @DeleteMapping("/author/{author}")
    public void deleteAllByAuthor(@PathVariable final String author) {
        bookRepository.findOneByAuthor(author)
            .orElseThrow(() -> new AuthorDoesNotHaveBooksException(author));
        bookRepository.deleteByAuthor(author);
    }

    @DeleteMapping("/publisher/{publisher}")
    public void deleteAllByPublisher(@PathVariable final String publisher) {
        bookRepository.findOneByPublisher(publisher)
            .orElseThrow(() -> new PublisherDoesNotHaveBooksException(publisher));
        bookRepository.deleteByPublisher(publisher);
    }

    @DeleteMapping("/isbn/{isbn}")
    public void deleteByIsbn(@PathVariable final String isbn) {
        bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new IsbnDoesNotBelongToAnyBookException(isbn));
        bookRepository.deleteByIsbn(isbn);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody final Book book, @PathVariable final Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException(book.getId(), id);
        }
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        return bookRepository.save(book);
    }
}
