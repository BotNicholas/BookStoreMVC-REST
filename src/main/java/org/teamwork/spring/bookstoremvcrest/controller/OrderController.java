package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.OrderDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.OrderServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> findAll() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(orderService.findByKey(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody OrderDTO orderDTO) throws UnexpectedIdException {
        if (orderDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        orderService.save(orderDTO);
        return new ResponseEntity<>("Save success!", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("id") Integer id) {
        orderService.update(id, orderDTO);
        return new ResponseEntity<>("Update successful!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        orderService.delete(id);
        return new ResponseEntity<>("Delete successful!", HttpStatus.OK);
    }
}
