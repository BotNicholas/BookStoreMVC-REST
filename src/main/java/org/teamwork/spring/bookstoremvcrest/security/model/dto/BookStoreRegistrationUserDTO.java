package org.teamwork.spring.bookstoremvcrest.security.model.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.teamwork.spring.bookstoremvcrest.model.dto.CostumerDTO;
import org.teamwork.spring.bookstoremvcrest.model.dto.DefaultDTO;

public class BookStoreRegistrationUserDTO implements DefaultDTO {
    private Integer id;

    @NotEmpty(message = "Specify the username!")
    private String username;

    @NotEmpty(message = "Specify password!")
//    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Use only locase and uppercase letters, numers and _ sign!")
    @Length(min = 5, message = "Minimal length is 5!")
    private String password;

    private CostumerDTO costumer;

    public BookStoreRegistrationUserDTO() {
    }

    public BookStoreRegistrationUserDTO(String username, String password, CostumerDTO costumer) {
        this.username = username;
        this.password = password;
        this.costumer = costumer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CostumerDTO getCostumer() {
        return costumer;
    }

    public void setCostumer(CostumerDTO costumer) {
        this.costumer = costumer;
    }

    @Override
    public String toString() {
        return "BookStoreUserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", costumer=" + costumer +
                '}';
    }
}
