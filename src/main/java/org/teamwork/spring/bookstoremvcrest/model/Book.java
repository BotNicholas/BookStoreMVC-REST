package org.teamwork.spring.bookstoremvcrest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Author must be specified!")
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;
    @NotNull(message = "Book category must be specified!")
    @ManyToOne
    @JoinColumn(name = "book_category_code", referencedColumnName = "code")
    private BookCategory category;
    @NotEmpty(message = "ISBN can not be empty!")
    @Pattern(regexp = "\\d\\d\\d-\\d-\\d\\d-\\d\\d\\d\\d\\d\\d-\\d", message = "ISBN must be in format: xxx-x-xx-xxxxxx-x")
    @Length(min = 17, max = 17)
    private String isbn;
    @NotNull(message = "Publication date must be specified!")
    @Column(name = "publication_date")
    private Date publicationDate;
    @NotNull(message = "Acquiring date must be specified!")
    @Column(name = "date_aquired")
    private Date dateAcquired;
    @NotEmpty(message = "Specify the title!")
    private String title;
    @Min(value = 0, message = "Price must be > than 0")
    @Column(name = "recommended_price")
    private Double recommendedPrice;
    private String comments;

    public Book() {
    }

    public Book(Author author, BookCategory category, String isbn, Date publicationDate, Date dateAcquired, String title, Double recommendedPrice, String comments) {
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.dateAcquired = dateAcquired;
        this.title = title;
        this.recommendedPrice = recommendedPrice;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(Date dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(Double recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author=" + author +
                ", category=" + category +
                ", isbn='" + isbn + '\'' +
                ", publicationDate=" + publicationDate +
                ", dateAcquired=" + dateAcquired +
                ", title='" + title + '\'' +
                ", recommendedPrice=" + recommendedPrice +
                ", comments='" + comments + '\'' +
                '}';
    }
}
