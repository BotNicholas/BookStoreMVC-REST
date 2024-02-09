package org.teamwork.spring.bookstoremvcrest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Firstname can not be empty!")
    private String firstname;
    @NotEmpty(message = "Lastname can not be empty!")
    private String lastname;
    @NotEmpty(message = "Only 2 symbols for initials are required!")
    @Length(min = 2, max = 2)
    private String initials;
    @NotNull(message = "Birthdate must be specified!")
    @Column(name = "birth_date")
    private Date birthDate;
    @NotEmpty(message = "Gender must be specified!")
    @Pattern(regexp = "^[MFU]$", message = "Gender must be in uppercase and only M(ale), F(emale) or U(ndefined)!")
    private Character gender;
    @Column(name = "contact_details")
    private String contactDetails;
    @Column(name = "other_details")
    private String otherDetails;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author() {
        this.books = new ArrayList<>();
    }

    public Author(String firstname, String lastname, String initials, Date birthDate, Character gender, String contactDetails, String otherDetails) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.initials = initials;
        this.birthDate = birthDate;
        this.gender = gender;
        this.contactDetails = contactDetails;
        this.otherDetails = otherDetails;
        this.books = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", initials='" + initials + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", contactDetails='" + contactDetails + '\'' +
                ", otherDetails='" + otherDetails + '\'' +
                '}';
    }
}
