package wolox.training.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book<Subtitle> {
    @Id
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

    public Book(String genre, String author, String image, String title, String subtitle,
        String publisher, String year, int pages) {
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = UUID.randomUUID().toString();
    }

    public String setGenre() {
        return genre;
    }

    public String setAuthor() {
        return author;
    }

    public String getImage() {
        return image;
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

    public void setGenre(String newGendre) {
        genre = newGendre;
    }

    public void setAuthor(String newAuthor) {
        author = newAuthor;
    }

    public void setImage(String newImage) {
        image = newImage;
    }

    public void setSubtitle(String newSubtitle) {
        subtitle = newSubtitle;
    }

    public void setPublisher(String newPublisher) {
        publisher = newPublisher;
    }

    public void setYear(String newYear) {
        year = newYear;
    }

    public void setPages(int newPages) {
        pages = newPages;
    }

}
