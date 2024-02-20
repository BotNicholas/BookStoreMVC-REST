package org.teamwork.spring.bookstoremvcrest.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.teamwork.spring.bookstoremvcrest.security.model.BookStoreUser;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class BookStoreUserDetails implements UserDetails {
    private BookStoreUser user;

    public BookStoreUserDetails(BookStoreUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRoles().split(",\\s+")).map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public BookStoreUser getUser() {
        return user;
    }

    public void setUser(BookStoreUser user) {
        this.user = user;
    }
}
