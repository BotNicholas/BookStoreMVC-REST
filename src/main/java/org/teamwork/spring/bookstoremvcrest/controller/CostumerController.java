package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.CostumerDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.CostumerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/costumers")
public class CostumerController {
    @Autowired
    private CostumerServiceImpl costumerService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<CostumerDTO> findAll() {
        return costumerService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CostumerDTO findById(@PathVariable("id") Integer id) {
        return costumerService.findByKey(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String save(@RequestBody CostumerDTO costumerDTO) throws UnexpectedIdException {
        if (costumerDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        costumerService.save(costumerDTO);
        return "Save success!";
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody CostumerDTO costumerDTO, @PathVariable("id") Integer id) {
        costumerService.update(id, costumerDTO);
        return "Update successful!";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private String delete(@PathVariable("id") Integer id) {
        costumerService.delete(id);
        return "Success!";
    }
}
