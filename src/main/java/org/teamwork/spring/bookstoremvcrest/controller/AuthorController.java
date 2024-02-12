package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapperImpl;
import org.teamwork.spring.bookstoremvcrest.model.Author;
import org.teamwork.spring.bookstoremvcrest.model.dto.AuthorDTO;
import org.teamwork.spring.bookstoremvcrest.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository repository;
    @Autowired
    private AbstractMapperImpl mapper;
    //todo: replace with service

    @GetMapping("")
    public ResponseEntity<List<AuthorDTO>> findAll() {
        List<Author> authors = repository.findAll();
        List<AuthorDTO> authorDTOS = authors.stream().map(author -> mapper.toDTO(author, AuthorDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id") Integer id) {
        Author author = repository.findById(id).orElse(null);
        AuthorDTO authorDTO =mapper.toDTO(author, AuthorDTO.class);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody AuthorDTO authorDTO) throws UnexpectedIdException {
        //verifying if object is present
//        if (repository.findById(authorDTO.getId()).isPresent()) {
//            throw new ObjectAlreadyPresentException();
//        }
        if (authorDTO.getId() != null) {
            throw new UnexpectedIdException();
        }

        Author author = mapper.toEntity(authorDTO, Author.class);
        Author savedAuthor = repository.save(author);
        return new ResponseEntity<>("Save success!", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody AuthorDTO authorDTO, @PathVariable Integer id) {
        Author author = repository.findById(id).orElse(new Author());
        author.setFirstname(authorDTO.getFirstname());
        author.setLastname(authorDTO.getLastname());
        author.setInitials(authorDTO.getInitials());
        author.setBirthDate(authorDTO.getBirthDate());
        author.setGender(authorDTO.getGender());
        author.setContactDetails(authorDTO.getContactDetails());
        author.setOtherDetails(authorDTO.getOtherDetails());
        repository.save(author); //will work as update
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        repository.deleteById(id);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
