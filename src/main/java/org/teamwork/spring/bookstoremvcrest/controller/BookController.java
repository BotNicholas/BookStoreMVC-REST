package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
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
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> findAll(){
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        BookDTO bookDTO = bookService.findByKey(id);
        if (bookDTO == null) {
            throw new NotFoundException();
        }
        return bookDTO;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody BookDTO bookDTO) throws UnexpectedIdException {
        if (bookDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        bookService.save(bookDTO);
        return "Success!";
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody BookDTO bookDTO, @PathVariable("id") Integer id){
        bookService.update(id, bookDTO);
        return "Success!";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Integer id){
        bookService.delete(id);
        return "Succsess!";
    }
}
