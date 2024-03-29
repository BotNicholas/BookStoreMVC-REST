package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.BookCategoryDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.BookCategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class BookCategoryController {
    private final BookCategoryServiceImpl categoryService;

    public BookCategoryController(BookCategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<BookCategoryDTO> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BookCategoryDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        BookCategoryDTO bookCategoryDTO = categoryService.findByKey(id);
        if (bookCategoryDTO == null) {
            throw new NotFoundException();
        }
        return bookCategoryDTO;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody BookCategoryDTO categoryDTO) throws UnexpectedIdException {
        if (categoryDTO.getCode() != null) {
            throw new UnexpectedIdException();
        }
        categoryService.save(categoryDTO);
        return "Success!";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody BookCategoryDTO categoryDTO, @PathVariable("id") Integer id) {
        categoryService.update(id, categoryDTO);
        return "Success!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return "Success!";
    }
}