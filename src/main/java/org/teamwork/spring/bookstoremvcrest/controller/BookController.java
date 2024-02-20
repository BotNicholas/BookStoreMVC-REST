package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> findAll(){
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        BookDTO bookDTO = bookService.findByKey(id);
        if (bookDTO == null) {
            throw new NotFoundException();
        }
        return bookDTO;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody BookDTO bookDTO) throws UnexpectedIdException {
        if (bookDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        bookService.save(bookDTO);
        return "Success!";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody BookDTO bookDTO, @PathVariable("id") Integer id){
        bookService.update(id, bookDTO);
        return "Success!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") Integer id){
        bookService.delete(id);
        return "Succsess!";
    }
}
