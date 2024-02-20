package org.teamwork.spring.bookstoremvcrest.security.services;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private BookStoreUserRepository repository;
    @Autowired
    private CostumerRepository costumerRepository;
    @Autowired
    private AbstractMapperImpl mapper;

    @Override
    public List<BookStoreUserDTO> findAll() {
        return repository.findAll().stream().map(user -> mapper.toDTO(user, BookStoreUserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BookStoreUserDTO findByKey(Integer key) {
        return mapper.toDTO(repository.findById(key), BookStoreUserDTO.class);
    }

    public BookStoreUserDTO findByUsername(String username) {
        return mapper.toDTO(repository.findByUsername(username), BookStoreUserDTO.class);
    }

    private BookStoreUserDTO save(BookStoreUser user){
        if (user.getCostumer() == null) {
            Costumer costumerForUser = costumerRepository.save(new Costumer(user.getUsername())); //creating empty costumer for new user
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

    public BookStoreUserDTO register(BookStoreRegistrationUserDTO registrationUserDTO){
        BookStoreUser bookStoreUser = mapper.toEntity(registrationUserDTO, BookStoreUser.class);
        bookStoreUser.setRoles("ROLE_USER"); //setting default Role
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
        //Delete user
        repository.deleteById(key);
        //Delete iss Costumer
        costumerRepository.deleteById(user.getCostumer().getId());
    }
}
