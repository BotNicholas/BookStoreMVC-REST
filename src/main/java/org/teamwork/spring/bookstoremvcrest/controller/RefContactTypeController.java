package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
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
    @ResponseStatus(HttpStatus.OK)
    public List<RefContactTypeDTO> findAll() {
        return contactTypeService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RefContactTypeDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        RefContactTypeDTO refContactTypeDTO = contactTypeService.findByKey(id);
        if (refContactTypeDTO == null) {
            throw new NotFoundException();
        }
        return contactTypeService.findByKey(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public String save(@RequestBody RefContactTypeDTO refContactTypeDTO) throws UnexpectedIdException {
        if (refContactTypeDTO.getCode() != null) {
            throw new UnexpectedIdException();
        }
        contactTypeService.save(refContactTypeDTO);

        return "Save successful!";
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody RefContactTypeDTO refContactTypeDTO, @PathVariable("id") Integer id) {
        contactTypeService.update(id, refContactTypeDTO);
        return "Update successful!";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Integer id) {
        contactTypeService.delete(id);
        return "Delete successful!";
    }
}
