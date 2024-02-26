package org.teamwork.spring.bookstoremvcrest.security.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapperImpl;
import org.teamwork.spring.bookstoremvcrest.model.Costumer;
import org.teamwork.spring.bookstoremvcrest.repository.CostumerRepository;
import org.teamwork.spring.bookstoremvcrest.security.model.BookStoreUser;
import org.teamwork.spring.bookstoremvcrest.security.model.dto.BookStoreRegistrationUserDTO;
import org.teamwork.spring.bookstoremvcrest.security.model.dto.BookStoreUserDTO;
import org.teamwork.spring.bookstoremvcrest.security.repositories.BookStoreUserRepository;
import org.teamwork.spring.bookstoremvcrest.service.DefaultService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookStoreUserServiceImpl implements DefaultService<BookStoreUserDTO, BookStoreUser, Integer> {
    private final BookStoreUserRepository repository;
    private final CostumerRepository costumerRepository;
    private final AbstractMapperImpl mapper;

    public BookStoreUserServiceImpl(BookStoreUserRepository repository, CostumerRepository costumerRepository, AbstractMapperImpl mapper) {
        this.repository = repository;
        this.costumerRepository = costumerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BookStoreUserDTO> findAll() {
        return repository.findAll().stream().map(user -> mapper.toDTO(user, BookStoreUserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BookStoreUserDTO findByKey(Integer key) {
        return mapper.toDTO(repository.findById(key).orElseThrow(null), BookStoreUserDTO.class);
    }

    public BookStoreUserDTO findByUsername(String username) {
        return mapper.toDTO(repository.findByUsername(username), BookStoreUserDTO.class);
    }

    private BookStoreUserDTO save(BookStoreUser user) {
        if (user.getCostumer() == null) {
            Costumer costumerForUser = costumerRepository.save(new Costumer(user.getUsername()));
            user.setCostumer(costumerForUser);
        }
        user = repository.save(user);
        return mapper.toDTO(user, BookStoreUserDTO.class);
    }

    @Override
    public BookStoreUserDTO save(BookStoreUserDTO obj) {
        BookStoreUser user = mapper.toEntity(obj, BookStoreUser.class);
        return save(user);
    }

    public BookStoreUserDTO register(BookStoreRegistrationUserDTO registrationUserDTO) {
        BookStoreUser bookStoreUser = mapper.toEntity(registrationUserDTO, BookStoreUser.class);
        bookStoreUser.setRoles("ROLE_USER");
        return save(bookStoreUser);
    }

    @Override
    public BookStoreUserDTO update(Integer key, BookStoreUserDTO obj) {
        BookStoreUser user = mapper.toEntity(obj, BookStoreUser.class);
        user.setId(key);
        repository.save(user);
        costumerRepository.save(user.getCostumer());//
        return mapper.toDTO(user, BookStoreUserDTO.class);
    }

    public BookStoreUserDTO update(String username, BookStoreUserDTO obj) {
        BookStoreUser user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Such user was not found!"));
        BookStoreUser updatedUser = mapper.toEntity(obj, BookStoreUser.class);
        updatedUser.setId(user.getId());
        repository.save(updatedUser);
        costumerRepository.save(updatedUser.getCostumer());//
        return mapper.toDTO(updatedUser, BookStoreUserDTO.class);
    }

    @Override
    public void delete(Integer key) {
        BookStoreUser user = repository.findById(key).orElseThrow(() -> new IllegalArgumentException("Such user does no exist!"));
        repository.deleteById(key);
        costumerRepository.deleteById(user.getCostumer().getId());
    }
}
