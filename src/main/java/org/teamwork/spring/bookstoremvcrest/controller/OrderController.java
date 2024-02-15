package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
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
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        OrderDTO orderDTO = orderService.findByKey(id);
        if (orderDTO == null) {
            throw new NotFoundException();
        }
        return orderService.findByKey(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public String save(@RequestBody OrderDTO orderDTO) throws UnexpectedIdException {
        if (orderDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        orderService.save(orderDTO);
        return "Save success!";
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("id") Integer id) {
        orderService.update(id, orderDTO);
        return "Update successful!";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Integer id) {
        orderService.delete(id);
        return "Delete successful!";
    }
}
