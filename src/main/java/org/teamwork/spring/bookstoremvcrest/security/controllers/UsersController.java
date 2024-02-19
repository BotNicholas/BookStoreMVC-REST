package org.teamwork.spring.bookstoremvcrest.security.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.security.model.dto.BookStoreUserDTO;
import org.teamwork.spring.bookstoremvcrest.security.services.BookStoreUserServiceImpl;

@RestController
public class UsersController {
    @Autowired
    private BookStoreUserServiceImpl service;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@Valid @RequestBody BookStoreUserDTO userDTO) throws UnexpectedIdException {
        if (userDTO.getId() != null) {
            throw new UnexpectedIdException("Id is unexpected here!");
        }
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        service.save(userDTO);
        return "Success!";
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public BookStoreUserDTO getPersonalData(Authentication authentication){
        return service.findByUsername(authentication.getName());
    }

    @PatchMapping("/me/edit")
    @ResponseStatus(HttpStatus.OK)
    public String updatePersonalData(Authentication authentication, @Valid @RequestBody BookStoreUserDTO bookStoreUserDTO) throws UnexpectedIdException {
        if (bookStoreUserDTO.getId() != null) {
            throw new UnexpectedIdException("Id is unexpected here!");
        }
        service.update(authentication.getName(), bookStoreUserDTO);
        authentication.setAuthenticated(false); //reset authentication
        return "Success!";
    }
}
