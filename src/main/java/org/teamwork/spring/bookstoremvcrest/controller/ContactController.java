package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ContactDTO>> findAll(){
        return new ResponseEntity<>(contactService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> findById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(contactService.findByKey(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@Valid @RequestBody ContactDTO contactDTO) throws UnexpectedIdException {
        if (contactDTO.getId() != null) {
            throw  new UnexpectedIdException();
        }
        contactService.save(contactDTO);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody ContactDTO contactDTO, @PathVariable("id") Integer id){
        contactService.update(id, contactDTO);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        contactService.delete(id);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
