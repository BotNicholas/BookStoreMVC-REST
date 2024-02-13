package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.OrderItemDTO;
import org.teamwork.spring.bookstoremvcrest.service.impl.OrderItemServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemServiceImpl orderItemService;

    @GetMapping()
    public ResponseEntity<List<OrderItemDTO>> findAll() {
        return new ResponseEntity<>(orderItemService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(orderItemService.findByKey(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody OrderItemDTO orderItemDTO) throws UnexpectedIdException {
        if (orderItemDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        orderItemService.save(orderItemDTO);
        return new ResponseEntity<>("Save successful!", HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody OrderItemDTO orderItemDTO, @PathVariable("id") Integer id) {
        orderItemService.update(id, orderItemDTO);
        return new ResponseEntity<>("Update successful!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        orderItemService.delete(id);
        return new ResponseEntity<>("Delete successful!", HttpStatus.OK);
    }
}
