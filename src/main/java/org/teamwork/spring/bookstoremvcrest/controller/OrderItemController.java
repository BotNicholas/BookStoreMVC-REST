package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
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
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemDTO> findAll() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderItemDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        OrderItemDTO orderItemDTO = orderItemService.findByKey(id);
        if (orderItemDTO == null) {
            throw new NotFoundException();
        }
        return orderItemService.findByKey(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public String save(@RequestBody OrderItemDTO orderItemDTO) throws UnexpectedIdException {
        if (orderItemDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        orderItemService.save(orderItemDTO);
        return "Save successful!";
    }
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody OrderItemDTO orderItemDTO, @PathVariable("id") Integer id) {
        orderItemService.update(id, orderItemDTO);
        return "Update successful!";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Integer id) {
        orderItemService.delete(id);
        return "Delete successful!";
    }
}
