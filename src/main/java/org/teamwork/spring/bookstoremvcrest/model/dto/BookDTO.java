package org.teamwork.spring.bookstoremvcrest.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class BookDTO {
    private Integer id;
    @NotNull(message = "Author must be specified!")
    private Integer authorId;
    @NotNull(message = "Book category must be specified!")
    private Integer categoryCode;
    @NotEmpty(message = "ISBN can not be empty!")
    @Pattern(regexp = "\\d\\d\\d-\\d-\\d\\d-\\d\\d\\d\\d\\d\\d-\\d", message = "ISBN must be in format: xxx-x-xx-xxxxxx-x")
    @Length(min = 17, max = 17)
    private String isbn;
    @NotEmpty(message = "Publication date must be specified!")
    private Date publicationDate;
    @NotEmpty(message = "Acquiring date must be specified!")
    private Date dateAcquired;
    @NotEmpty(message = "Specify the title!")
    private String title;
    @Min(value = 0, message = "Price must be > than 0")
    private Double recommendedPrice;
    private String comments;

    public BookDTO() {
    }

    public BookDTO(Integer authorId, Integer categoryCode, String isbn, Date publicationDate, Date dateAcquired, String title, Double recommendedPrice, String comments) {
        this.authorId = authorId;
        this.categoryCode = categoryCode;
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

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
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
        return "BookDTO{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", categoryCode=" + categoryCode +
                ", isbn='" + isbn + '\'' +
                ", publicationDate=" + publicationDate +
                ", dateAcquired=" + dateAcquired +
                ", title='" + title + '\'' +
                ", recommendedPrice=" + recommendedPrice +
                ", comments='" + comments + '\'' +
                '}';
    }
}
