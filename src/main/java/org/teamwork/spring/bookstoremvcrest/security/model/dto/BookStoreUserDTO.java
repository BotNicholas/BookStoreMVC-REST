package org.teamwork.spring.bookstoremvcrest.security.model.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.teamwork.spring.bookstoremvcrest.model.dto.CostumerDTO;
import org.teamwork.spring.bookstoremvcrest.model.dto.DefaultDTO;

public class BookStoreUserDTO implements DefaultDTO {
    private Integer id;

    @NotEmpty(message = "Specify the username!")
    private String username;

    @NotEmpty(message = "Specify password!")
//    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Use only locase and uppercase letters, numers and _ sign!")
    @Length(min = 5, message = "Minimal length is 5!")
    private String password;

    @NotEmpty
    private String roles;

    private CostumerDTO costumer;

    public BookStoreUserDTO() {
    }

    public BookStoreUserDTO(String username, String password, String roles, CostumerDTO costumer) {
        this.username = username;
        this.password = password;
        this.roles = roles;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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
                ", roles='" + roles + '\'' +
                ", costumer=" + costumer +
                '}';
    }
}
