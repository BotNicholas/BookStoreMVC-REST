package org.teamwork.spring.bookstoremvcrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapper;
import org.teamwork.spring.bookstoremvcrest.model.Book;
import org.teamwork.spring.bookstoremvcrest.model.BookCategory;
import org.teamwork.spring.bookstoremvcrest.model.dto.BookCategoryDTO;
import org.teamwork.spring.bookstoremvcrest.repository.BookCategoryRepository;
import org.teamwork.spring.bookstoremvcrest.service.DefaultService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookCategoryServiceImpl implements DefaultService<BookCategoryDTO, BookCategory, Integer> {
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private AbstractMapper mapper;
    @Override
    public List<BookCategoryDTO> findAll() {
        List<BookCategory> bookCategories = bookCategoryRepository.findAll();
        List<BookCategoryDTO> bookCategoryDTOS = bookCategories.stream().map(bookCategory -> mapper.toDTO(bookCategory, BookCategoryDTO.class)).collect(Collectors.toList());

        return bookCategoryDTOS;
    }

    @Override
    public BookCategoryDTO findByKey(Integer key) {
        return mapper.toDTO(bookCategoryRepository.findById(key), BookCategoryDTO.class);
    }

    @Override
    public BookCategoryDTO save(BookCategoryDTO obj) {
        BookCategory bookCategory = bookCategoryRepository.save(mapper.toEntity(obj, BookCategory.class));
        return mapper.toDTO(bookCategory, BookCategoryDTO.class);
    }

    @Override
    public BookCategoryDTO update(Integer key, BookCategoryDTO obj) {
        BookCategory bookCategory = bookCategoryRepository.findById(key).orElse(new BookCategory());

        bookCategory.setCategoryDescription(obj.getCategoryDescription());
        bookCategoryRepository.save(bookCategory);

        return mapper.toDTO(bookCategory, BookCategoryDTO.class);
    }

    @Override
    public void delete(Integer key) {
        bookCategoryRepository.deleteById(key);
    }
}
