package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.ContactDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.ContactServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    private ContactServiceImpl contactService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactDTO> findAll(){
        return contactService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO findById(@PathVariable("id") Integer id){
        return contactService.findByKey(id);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String save(@Valid @RequestBody ContactDTO contactDTO) throws UnexpectedIdException {
        if (contactDTO.getId() != null) {
            throw  new UnexpectedIdException();
        }
        contactService.save(contactDTO);
        return "Success!";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody ContactDTO contactDTO, @PathVariable("id") Integer id){
        contactService.update(id, contactDTO);
        return "Success!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") Integer id){
        contactService.delete(id);
        return "Success!";
    }
}
