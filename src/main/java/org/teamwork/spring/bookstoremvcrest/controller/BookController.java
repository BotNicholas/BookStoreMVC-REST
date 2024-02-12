package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.BookDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.BookServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> findAll(){
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(bookService.findByKey(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@Valid @RequestBody BookDTO bookDTO) throws UnexpectedIdException {
        if (bookDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        bookService.save(bookDTO);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody BookDTO bookDTO, @PathVariable("id") Integer id){
        bookService.update(id, bookDTO);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        bookService.delete(id);
        return new ResponseEntity<>("Succsess!", HttpStatus.OK);
    }
}
