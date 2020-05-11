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
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

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
        return bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);
    }

    @GetMapping("/isbn/{isbn}")
    public Book findOne(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
            .orElseThrow(IsbnDoesNotBelongToAnyBookException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @DeleteMapping("/author/{author}")
    public void delete(@PathVariable String author) {
        bookRepository.findOneByAuthor(author)
            .orElseThrow(AuthorDoesNotHaveBooksException::new);
        bookRepository.deleteByAuthor(author);
    }

    @DeleteMapping("/publisher/{publisher}")
    public void delete(@PathVariable String publisher) {
        bookRepository.findOneByPublisher(publisher)
            .orElseThrow(PublisherDoesNotHaveBooksException::new);
        bookRepository.deleteByPublisher(publisher);
    }

    @DeleteMapping("/isbn/{isbn}")
    public void delete(@PathVariable String isbn) {
        bookRepository.findByIsbn(isbn)
            .orElseThrow(IsbnDoesNotBelongToAnyBookException::new);
        bookRepository.deleteByIsbn(isbn);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}
