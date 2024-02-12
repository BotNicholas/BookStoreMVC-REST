package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.BookCategoryDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.BookCategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class BookCategoryController {
    @Autowired
    private BookCategoryServiceImpl categoryService;

    @GetMapping("")
    public ResponseEntity<List<BookCategoryDTO>> findAll(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCategoryDTO> findById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(categoryService.findByKey(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@Valid @RequestBody BookCategoryDTO categoryDTO) throws UnexpectedIdException {
        if (categoryDTO.getCode() != null) {
            throw new UnexpectedIdException();
        }
        categoryService.save(categoryDTO);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody BookCategoryDTO categoryDTO, @PathVariable("id") Integer id){
        categoryService.update(id, categoryDTO);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        categoryService.delete(id);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
