package wolox.training.models;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
    private static final int MAX_AGE = 150;
    private static final int MAX_NAME_LENGTH = 70;
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_USERNAME_LENGTH = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @JoinTable(name = "book_users",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "users_id",
            referencedColumnName = "id"))
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Book> books;

    @Column(nullable = false)
    private LocalDate birthDate;

    public User(){}

    public User(final String username, final String name, final LocalDate birthDate) {
        this.username = username;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return this.id;
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
        checkNotNull(username, "Username can not be null.");
        Preconditions.checkArgument(((username.length() <= MAX_USERNAME_LENGTH) && (username.length() >= MIN_USERNAME_LENGTH)),
            "Username must have between " + MIN_USERNAME_LENGTH + " and " + MAX_USERNAME_LENGTH + " characters.");
        Preconditions.checkArgument(username.matches("^.*[a-zA-Z]+.*$"),
            "Username should at least one letter.");
        Preconditions.checkArgument(username.matches("^[a-zA-Z0-9._]*$"),
            "Username should have only letters, numbers, dots or underscores.");

        this.username = username;
    }

    public void setBirthDate(final LocalDate birthDate) {
        checkNotNull(birthDate, "Birth date can not be null.");
        Preconditions.checkArgument(birthDate.isBefore(LocalDate.now()), "Birth date should be previous than today.");
        Preconditions.checkArgument((LocalDate.now().getYear() - birthDate.getYear()) < MAX_AGE,
            "User can not have more than " + MAX_AGE+ " years.");
        this.birthDate = birthDate;
    }

    public void addBook(Book book) {
        checkNotNull(book, "Book can not be null.");
        books.add(book);
    }

    public void setBooks(List<Book> books) {
        checkNotNull(books, "Books can not be null.");
        this.books = books;
    }
    public void deleteBook(Book book) {
        checkNotNull(book, "Book can not be null.");
        books.removeIf(currentBook -> (currentBook.getId() == book.getId()));
    }

    public void setName(final String name) {
        checkNotNull(name, "Name can not be null.");
        Preconditions.checkArgument(((name.length() <= MAX_NAME_LENGTH) && (name.length() >= MIN_NAME_LENGTH)),
            "Name must have between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
        Preconditions.checkArgument(name.matches("^[ A-Za-z]+$"), "Name must have only letters and spaces.");
        this.name = name;
    }
}
