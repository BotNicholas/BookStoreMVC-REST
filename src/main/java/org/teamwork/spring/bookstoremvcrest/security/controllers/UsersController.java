package org.teamwork.spring.bookstoremvcrest.security.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.security.model.dto.BookStoreRegistrationUserDTO;
import org.teamwork.spring.bookstoremvcrest.security.model.dto.BookStoreUserDTO;
import org.teamwork.spring.bookstoremvcrest.security.services.BookStoreUserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private BookStoreUserServiceImpl service;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<BookStoreUserDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BookStoreUserDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        BookStoreUserDTO userDTO =  service.findByKey(id);
        if (userDTO == null) {
            throw new NotFoundException("Such user was not found!");
        }
        return userDTO;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody BookStoreUserDTO bookStoreUserDTO) throws UnexpectedIdException {
        if (bookStoreUserDTO.getId() != null) {
            throw new UnexpectedIdException("Id is unexpected for User");
        }
        bookStoreUserDTO.setPassword(encoder.encode(bookStoreUserDTO.getPassword()));
        service.save(bookStoreUserDTO);
        return "Success!";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String update(@PathVariable("id") Integer id, @Valid @RequestBody BookStoreUserDTO bookStoreUserDTO){
        service.update(id, bookStoreUserDTO);
        return "Success!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Integer id){
        service.delete(id);
        return "Delete successful!";
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@Valid @RequestBody BookStoreRegistrationUserDTO userDTO) throws UnexpectedIdException {
        if (userDTO.getId() != null) {
            throw new UnexpectedIdException("Id is unexpected here!");
        }
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        service.register(userDTO);
        return "Success!";
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public BookStoreUserDTO getPersonalData(Authentication authentication){
        return service.findByUsername(authentication.getName());
    }

    @PatchMapping("/me")
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
