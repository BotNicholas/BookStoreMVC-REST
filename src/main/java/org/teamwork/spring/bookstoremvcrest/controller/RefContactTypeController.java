package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.RefContactTypeDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.RefContactTypeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/ref-contact-types")
public class RefContactTypeController {
    @Autowired
    private RefContactTypeServiceImpl contactTypeService;

    @GetMapping()
    public ResponseEntity<List<RefContactTypeDTO>> findAll() {
        return new ResponseEntity<>(contactTypeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefContactTypeDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(contactTypeService.findByKey(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody RefContactTypeDTO refContactTypeDTO) throws UnexpectedIdException {
        if (refContactTypeDTO.getCode() != null) {
            throw new UnexpectedIdException();
        }
        contactTypeService.save(refContactTypeDTO);

        return new ResponseEntity<>("Save successful!", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody RefContactTypeDTO refContactTypeDTO, @PathVariable("id") Integer id) {
        contactTypeService.update(id, refContactTypeDTO);
        return new ResponseEntity<>("Update successful!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        contactTypeService.delete(id);
        return new ResponseEntity<>("Delete successful!", HttpStatus.OK);
    }
}
