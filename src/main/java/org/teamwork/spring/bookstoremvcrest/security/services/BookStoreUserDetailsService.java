package org.teamwork.spring.bookstoremvcrest.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.security.details.BookStoreUserDetails;
import org.teamwork.spring.bookstoremvcrest.security.repositories.BookStoreUserRepository;

@Service
public class BookStoreUserDetailsService implements UserDetailsService {
    @Autowired
    private BookStoreUserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new BookStoreUserDetails(repository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Such user was not found!")));
    }
}
