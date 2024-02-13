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
    public ResponseEntity<List<CostumerDTO>> findAll() {
        return new ResponseEntity<>(costumerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostumerDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(costumerService.findByKey(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody CostumerDTO costumerDTO) throws UnexpectedIdException {
        if (costumerDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        costumerService.save(costumerDTO);
        return new ResponseEntity<>("Save success !", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody CostumerDTO costumerDTO, @PathVariable("id") Integer id) {
        costumerService.update(id, costumerDTO);
        return new ResponseEntity<>("Update successful!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        costumerService.delete(id);
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
