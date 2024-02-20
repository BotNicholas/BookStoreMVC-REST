package org.teamwork.spring.bookstoremvcrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapper;
import org.teamwork.spring.bookstoremvcrest.model.Author;
import org.teamwork.spring.bookstoremvcrest.model.Book;
import org.teamwork.spring.bookstoremvcrest.model.BookCategory;
import org.teamwork.spring.bookstoremvcrest.model.dto.BookDTO;
import org.teamwork.spring.bookstoremvcrest.repository.AuthorRepository;
import org.teamwork.spring.bookstoremvcrest.repository.BookCategoryRepository;
import org.teamwork.spring.bookstoremvcrest.repository.BookRepository;
import org.teamwork.spring.bookstoremvcrest.service.DefaultService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements DefaultService<BookDTO, Book, Integer> {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookCategoryRepository categoryRepository;
    @Autowired
    private AbstractMapper mapper;
    @Override
    public List<BookDTO> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> booksDTO = books.stream().map(book -> mapper.toDTO(book, BookDTO.class)).collect(Collectors.toList());
        return booksDTO;
    }

    @Override
    public BookDTO findByKey(Integer key) {
        return mapper.toDTO(bookRepository.findById(key), BookDTO.class);
    }

    @Override
    public BookDTO save(BookDTO obj) {
        Book book = bookRepository.save(mapper.toEntity(obj, Book.class));
        return mapper.toDTO(book, BookDTO.class);
    }

    @Override
    public BookDTO update(Integer key, BookDTO obj) {
        Book book = mapper.toEntity(obj, Book.class);
        book.setId(key);
        bookRepository.save(book);
        return mapper.toDTO(book, BookDTO.class);
    }

    @Override
    public void delete(Integer key) {
        bookRepository.deleteById(key);
    }
}
