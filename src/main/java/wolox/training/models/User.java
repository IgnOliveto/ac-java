package wolox.training.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @OneToMany
    private List<Book> books;

    @Column(nullable = false)
    private LocalDate birthDate;

    public User(){}

    public User(final String username, final LocalDate birthDate) {
        this.username = username;
        this.birthDate = birthDate;
        this.books = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public String getName() {
        return name;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException(this.username, book);
        }
        books.add(book);
    }

    public void setName(final String name) {
        this.name = name;
    }
}
