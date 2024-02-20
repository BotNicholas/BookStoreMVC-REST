package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.AuthorDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.AuthorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorServiceImpl authorService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAuthority('ROLE_USER') || hasAuthority('ROLE_MANAGER') || hasAuthority('ROLE_ADMIN')")
    //OR
//    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_ADMIN')")
    //OR
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public List<AuthorDTO> findAll() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        AuthorDTO authorDTO = authorService.findByKey(id);
        if (authorDTO == null) {
            throw new NotFoundException();
        }
        return authorDTO;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody AuthorDTO authorDTO) throws UnexpectedIdException {
        if (authorDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        authorService.save(authorDTO);
        return "Save success!";
    }

    @PatchMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //OR
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody AuthorDTO authorDTO, @PathVariable Integer id) {
        authorService.update(id, authorDTO);
        return "Success!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") Integer id){
        authorService.delete(id);
        return "Deleted successful!";
    }
}
