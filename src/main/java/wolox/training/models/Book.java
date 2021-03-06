package wolox.training.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

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

    @ManyToMany(mappedBy = "books", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
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
        genre = newGenre;
    }

    public void setAuthor(final String newAuthor) {
        author = newAuthor;
    }

    public void setImage(final String newImage) {
        image = newImage;
    }

    public void setSubtitle(final String newSubtitle) {
        subtitle = newSubtitle;
    }

    public void setTitle(final String newTitle) {
        title = newTitle;
    }

    public void setPublisher(final String newPublisher) {
        publisher = newPublisher;
    }

    public void setYear(final String newYear) {
        year = newYear;
    }

    public void setPages(final int newPages) {
        pages = newPages;
    }

    public void setIsbn(final String newIsbn) {
        isbn = newIsbn;
    }

}
