package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public List<AuthorDTO> findAll() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTO findById(@PathVariable("id") Integer id) {
        return authorService.findByKey(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String save(@Valid @RequestBody AuthorDTO authorDTO) throws UnexpectedIdException {
        if (authorDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        authorService.save(authorDTO);
        return "Save success!";
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody AuthorDTO authorDTO, @PathVariable Integer id) {
        authorService.update(id, authorDTO);
        return "Success!";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") Integer id){
        authorService.delete(id);
        return "Success!";
    }
}
