package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.Order;
import org.teamwork.spring.bookstoremvcrest.model.dto.OrderDTO;
import org.teamwork.spring.bookstoremvcrest.model.dto.OrderItemDTO;
import org.teamwork.spring.bookstoremvcrest.security.details.BookStoreUserDetails;
import org.teamwork.spring.bookstoremvcrest.service.impl.OrderItemServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemServiceImpl orderItemService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemDTO> findAll() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public OrderItemDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        OrderItemDTO orderItemDTO = orderItemService.findByKey(id);
        if (orderItemDTO == null) {
            throw new NotFoundException();
        }
        return orderItemService.findByKey(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody OrderItemDTO orderItemDTO) throws UnexpectedIdException {
        if (orderItemDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        orderItemService.save(orderItemDTO);
        return "Save successful!";
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody OrderItemDTO orderItemDTO, @PathVariable("id") Integer id) {
        orderItemService.update(id, orderItemDTO);
        return "Update successful!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") Integer id) {
        orderItemService.delete(id);
        return "Delete successful!";
    }

    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemDTO> findMy(Authentication authentication){
        BookStoreUserDetails userDetails = (BookStoreUserDetails) authentication.getPrincipal();
        return orderItemService.findAllByCostumer(userDetails.getUser().getCostumer());
    }
}
