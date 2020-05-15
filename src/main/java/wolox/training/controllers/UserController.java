package wolox.training.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.EmptyNeccessaryAttributesException;
import wolox.training.exceptions.FailedToCreateUserException;
import wolox.training.exceptions.UserAlreadyExistsException;
import wolox.training.exceptions.UserDoesNotExistException;
import wolox.training.exceptions.UserDoesNotHaveAnyBookException;
import wolox.training.exceptions.UserDoesNotHaveTheBookException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/username/{username}")
    public User findByUsername(@PathVariable final String username) {
        return userRepository.findOneByUsername(username)
            .orElseThrow(() -> new UserDoesNotExistException(username));
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody final User user) {
        try {
            if (userRepository.findOneByUsername(user.getUsername()).isPresent()) {
                throw new UserAlreadyExistsException(user.getUsername());
            }
            User userToCreate = user;
            userToCreate.setBooks(new ArrayList<>());
            return userRepository.save(userToCreate);
        } catch(Exception e) {
            throw new FailedToCreateUserException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final Long id) {
        userRepository.findById(id)
            .orElseThrow(() -> new UserDoesNotExistException(id));
        userRepository.deleteById(id);
    }

    @DeleteMapping("/username/{username}")
    public void deleteByUsername(@PathVariable final String username) {
        User userToDelete = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserDoesNotExistException(username));
        userRepository.deleteById(userToDelete.getId());
    }

    @PutMapping("/{id}")
    public User updateUserAttributes(@RequestBody final User user, @PathVariable final Long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException(user.getId(), id);
        }
        User userToUpdate = userRepository.findById(user.getId())
            .orElseThrow(() -> new UserDoesNotExistException(user.getUsername()));
        userToUpdate.setBirthDate(user.getBirthDate());
        userToUpdate.setName(user.getName());
        userToUpdate.setUsername(user.getUsername());
        return userRepository.save(userToUpdate);
    }

    private User addBook(Book book, User user) {
       for (Book currentBook : user.getBooks()) {
            if (currentBook.getId() == book.getId()) {
                throw new BookAlreadyOwnedException(user.getUsername(), book);
            }
        }
        user.addBook(book);
        return user;
    }

    @PutMapping("/books/add/{id}")
    public User addBookToUser(@RequestBody final Book book, @PathVariable final Long id) {
        bookRepository.findById(book.getId())
            .orElseThrow(() -> new BookNotFoundException(book.getId()));
        User userToUpdate = userRepository.findById(id)
            .orElseThrow(() -> new UserDoesNotExistException(id));
        User updatedUser = addBook(book, userToUpdate);
        return userRepository.save(updatedUser);
    }

    @PutMapping("/books/delete/{id}")
    public User deleteBookToUser(@RequestBody final Book book, @PathVariable final Long id) {
        User userToUpdate = userRepository.findById(id)
            .orElseThrow(() -> new UserDoesNotExistException(id));
        if (userToUpdate.getBooks().isEmpty()) {
            throw new UserDoesNotHaveAnyBookException(id);
        }
        long booksTotalLength = userToUpdate.getBooks().size();
        userToUpdate.deleteBook(book);
        if (userToUpdate.getBooks().size() == booksTotalLength) {
            throw new UserDoesNotHaveTheBookException(book);
        }
        userRepository.save(userToUpdate);
        return userToUpdate;
    }
}
