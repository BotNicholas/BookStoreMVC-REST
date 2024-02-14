package org.teamwork.spring.bookstoremvcrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapper;
import org.teamwork.spring.bookstoremvcrest.model.Author;
import org.teamwork.spring.bookstoremvcrest.model.dto.AuthorDTO;
import org.teamwork.spring.bookstoremvcrest.repository.AuthorRepository;
import org.teamwork.spring.bookstoremvcrest.service.DefaultService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements DefaultService<AuthorDTO, Author, Integer> {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AbstractMapper mapper;

    @Override
    public List<AuthorDTO> findAll() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorsDTOS = authors.stream().map(author -> mapper.toDTO(author, AuthorDTO.class)).collect(Collectors.toList());
        return authorsDTOS;
    }

    @Override
    public AuthorDTO findByKey(Integer key) {
        return mapper.toDTO(authorRepository.findById(key).orElse(null), AuthorDTO.class);
    }

    @Override
    public AuthorDTO save(AuthorDTO obj) {
        Author author = authorRepository.save(mapper.toEntity(obj, Author.class));
        return mapper.toDTO(author, AuthorDTO.class);
    }

    @Override
    public AuthorDTO update(Integer key, AuthorDTO authorDTO) {
        Author author = mapper.toEntity(authorDTO, Author.class);
        author.setId(key);
        authorRepository.save(author);
        return mapper.toDTO(author, AuthorDTO.class);
    }

    @Override
    public void delete(Integer key) {
        authorRepository.deleteById(key);
    }
}
