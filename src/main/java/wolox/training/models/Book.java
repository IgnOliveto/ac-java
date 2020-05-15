package wolox.training.models;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
@ApiModel(description = "Books from the OpenLibraryAPI.")
public class Book {

    private static final int ISBN_LENGTH = 13;
    private static final int MAX_NAME_LENGTH = 70;
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_TITLE_LENGTH = 60;
    private static final int MIN_TITLE_LENGTH = 1;
    private static final int MIN_PAGES_NUMBER = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String isbn;

    @ApiModelProperty(value = "The book genre, could be horror, fantasy, comedy, etc.")
    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private int pages;

    @ManyToMany
    @JsonIgnore
    @JoinColumn(name="book_id")
    private List<User> users;

    public Book(){}

    public Book(final String genre, final String author, final String image, final String title, final String subtitle,
        final String publisher, final String year, final int pages, final String isbn) {
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
        this.users = new ArrayList<>();
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getYear() {
        return year;
    }

    public List<User> users() {
        return Collections.unmodifiableList(users);
    }

    public void setUsers(List<User> users) {
        checkNotNull(users, "Users can not be null.");
        this.users = users;
    }

    public int getPages() {
        return pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public Long getId() {
        return id;
    }

    public void setGenre(final String newGenre) {
        checkNotNull(newGenre, "Genre can not be null.");
        if (newGenre.length() > 0) {
            Preconditions.checkArgument(newGenre.matches("^[ A-Za-z]+$"),
                "Genre must have only letters and spaces.");
        }
        genre = newGenre;
    }

    public void setAuthor(final String newAuthor) {
        checkNotNull(newAuthor, "Author can not be null.");
        Preconditions.checkArgument(newAuthor.matches("^[ A-Za-z]+$"),
            "Author name must have only letters and spaces.");
        Preconditions.checkArgument(((newAuthor.length() <= MAX_NAME_LENGTH) && (newAuthor.length() >= MIN_NAME_LENGTH)),
            "Author name must have between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
        author = newAuthor;
    }

    public void setImage(final String newImage) {
        checkNotNull(newImage, "Image can not be null.");
        image = newImage;
    }

    public void setSubtitle(final String newSubtitle) {
        checkNotNull(newSubtitle, "Subtitle can not be null.");
        Preconditions.checkArgument(((newSubtitle.length() <= MAX_TITLE_LENGTH) && (newSubtitle.length() >= MIN_TITLE_LENGTH)),
            "Subtitle must have between " + MIN_TITLE_LENGTH + " and " + MAX_TITLE_LENGTH + " characters.");
        subtitle = newSubtitle;
    }

    public void setTitle(final String newTitle) {
        checkNotNull(newTitle, "Title can not be null.");
        Preconditions.checkArgument(((newTitle.length() <= MAX_TITLE_LENGTH) && (newTitle.length() >= MIN_TITLE_LENGTH)),
            "Title must have between " + MIN_TITLE_LENGTH + " and " + MAX_TITLE_LENGTH + " characters.");
        title = newTitle;
    }

    public void setPublisher(final String newPublisher) {
        checkNotNull(newPublisher, "Publisher can not be null.");
        Preconditions.checkArgument(((newPublisher.length() <= MAX_NAME_LENGTH) && (newPublisher.length() >= MIN_NAME_LENGTH)),
            "Publisher must have between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
        publisher = newPublisher;
    }

    public void setYear(final String newYear) {
        checkNotNull(newYear, "Year can not be null.");
        Preconditions.checkArgument(newYear.matches("^[0-9]+$"), "Year must have only numbers.");
        Preconditions.checkArgument(Integer.parseInt(newYear) <= LocalDate.now().getYear(),
            "Year should be equal or less than the current year.");
        year = newYear;
    }

    public void setPages(final int newPages) {
        checkNotNull(newPages, "Pages can not be null.");
        Preconditions.checkArgument(newPages >= MIN_PAGES_NUMBER, "Pages should be greater than " + MIN_PAGES_NUMBER +".");
        pages = newPages;
    }

    public void setIsbn(final String newIsbn) {
        checkNotNull(newIsbn, "ISBN can not be null.");
        Preconditions.checkArgument(newIsbn.matches("^[0-9]+$"), "ISBN must have only numbers.");
        Preconditions.checkArgument(newIsbn.length() == ISBN_LENGTH, "ISBN must have " + ISBN_LENGTH + " characters.");
        isbn = newIsbn;
    }

}
