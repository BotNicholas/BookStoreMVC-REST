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
    public ResponseEntity<List<AuthorDTO>> findAll() {
        return new ResponseEntity<>(authorService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(authorService.findByKey(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@Valid @RequestBody AuthorDTO authorDTO) throws UnexpectedIdException {
        if (authorDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        authorService.save(authorDTO);
        return new ResponseEntity<>("Save success!", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody AuthorDTO authorDTO, @PathVariable Integer id) {
        authorService.update(id, authorDTO);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        authorService.delete(id);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
