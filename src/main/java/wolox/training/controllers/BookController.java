package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @ApiOperation(value = "Retrieves all books.", response = Iterable.class)
    @ApiResponse(code = 200, message = "OK")
    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @ApiOperation(value = "Retrieves all books by the title.", response = List.class)
    @ApiResponse(code = 200, message = "OK")
    @GetMapping("/title/{bookTitle}")
    public List findByTitle(
        @ApiParam(value = "Title to find the books", required = true)
        @PathVariable final String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @ApiOperation(value = "Retrieves one book by the author.", response = Book.class)
    @ApiResponse(code = 200, message = "OK")
    @GetMapping("/author/{bookAuthor}")
    public Book findOneByAuthor(
        @ApiParam(value = "Author name to find the book", required = true)
        @PathVariable final String bookAuthor) {
            return bookRepository.findOneByAuthor(bookAuthor)
                .orElseThrow(() -> new AuthorDoesNotHaveBooksException(bookAuthor));
    }

    @ApiOperation(value = "Retrieves one book by the id.", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Book not found for ID.")
    })
    @GetMapping("/{id}")
    public Book findOne(
        @ApiParam(value = "Id to find the book to retrieve.", required = true)
        @PathVariable final Long id) {
            return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @ApiOperation(value = "Retrieves one book by the isbn.", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "ISBN does not match with any book.")
    })
    @GetMapping("/isbn/{isbn}")
    public Book findOne(
        @ApiParam(value = "ISBN to find book and retrieve.", required = true)
        @PathVariable final String isbn) {
        return bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new IsbnDoesNotBelongToAnyBookException(isbn));
    }

    @ApiOperation(value = "Creates a book.", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 409, message = "Book already exists."),
        @ApiResponse(code = 400, message = "Book could not be created.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@ApiParam(value = "The book to create.", required = true) @RequestBody final Book book) {
        try {
            if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
                throw new BookAlreadyExistsException(book.getIsbn());
            }
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new FailedToCreateBookException(e);
        }
    }

    @ApiOperation(value = "Deletes a book by id.", response = void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Book not found for ID.")
    })
    @DeleteMapping("/{id}")
    public void deleteById(
        @ApiParam(value = "Book to delete id", required = true)
        @PathVariable final Long id) {
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.deleteById(id);
    }

    @ApiOperation(value = "Deletes all books written by some author.", response = void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Book not found for the author."),
        @ApiResponse(code = 400, message = "Book could not be deleted.")
    })
    @DeleteMapping("/author/{author}")
    public void deleteAllByAuthor(
        @ApiParam(value = "author name to delete the books", required = true)
        @PathVariable final String author) {
        try {
            List<Book> booksToDelete = bookRepository.findByAuthor(author);
            if (booksToDelete.isEmpty()) {
                throw new AuthorDoesNotHaveBooksException(author);
            }
            for (Book book : booksToDelete) {
                bookRepository.deleteById(book.getId());
            }
        } catch (Exception e) {
            throw new FailedToDeleteBookException(e);
        }
    }

    @ApiOperation(value = "Deletes all books from a publisher.", response = void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Book not found for the publisher."),
        @ApiResponse(code = 400, message = "Book could not be deleted.")
    })
    @DeleteMapping("/publisher/{publisher}")
    public void deleteAllByPublisher(
        @ApiParam(value = "Publisher to delete the books", required = true)
        @PathVariable final String publisher) {
        try {
            List<Book> booksToDelete = bookRepository.findByPublisher(publisher);
            if (booksToDelete.isEmpty()) {
                throw new PublisherDoesNotHaveBooksException(publisher);
            }
            for (Book book : booksToDelete) {
                bookRepository.deleteById(book.getId());
            }
        } catch (Exception e) {
            throw new FailedToDeleteBookException(e);
        }
    }

    @ApiOperation(value = "Deletes a book by its ISBN.", response = void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "ISBN does not match with any book."),
        @ApiResponse(code = 400, message = "Book could not be deleted.")
    })
    @DeleteMapping("/isbn/{isbn}")
    public void deleteByIsbn(
        @ApiParam(value = "The book to delete ISBN.", required = true)
        @PathVariable final String isbn) {
        try {
            bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IsbnDoesNotBelongToAnyBookException(isbn));
            bookRepository.deleteByIsbn(isbn);
        } catch (Exception e) {
            throw new FailedToDeleteBookException(e);
        }
    }

    @ApiOperation(value = "Deletes a book by its ISBN.", response = void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Body and params data do not match."),
        @ApiResponse(code = 400, message = "Book not found for ID.")
    })
    @PutMapping("/{id}")
    public Book updateBook(
        @ApiParam(value = "The updated book.", required = true) @RequestBody final Book book,
        @ApiParam(value = "The book ID to update.", required = true) @PathVariable final Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException(book.getId(), id);
        }
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        return bookRepository.save(book);
    }
}
