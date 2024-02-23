package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.Costumer;
import org.teamwork.spring.bookstoremvcrest.model.dto.LightOrderItemDTO;
import org.teamwork.spring.bookstoremvcrest.model.dto.FullOrderDTO;
import org.teamwork.spring.bookstoremvcrest.model.dto.MyFullOrderDTO;
import org.teamwork.spring.bookstoremvcrest.security.details.BookStoreUserDetails;
import org.teamwork.spring.bookstoremvcrest.service.impl.OrderServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderServiceImpl service;
    @GetMapping("")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<FullOrderDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public FullOrderDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        FullOrderDTO fullOrderDTO = service.findByKey(id);
        if (fullOrderDTO == null) {
            throw new NotFoundException();
        }
        return fullOrderDTO;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@Valid @RequestBody FullOrderDTO fullOrderDTO) throws UnexpectedIdException {
        if (fullOrderDTO.getId() != null) {
            throw new UnexpectedIdException("Id is not expected here");
        }
        for (LightOrderItemDTO orderItemDTO : fullOrderDTO.getItemList()) {
            if (orderItemDTO.getId() != null) {
                throw new UnexpectedIdException("Id is unexpected in OrderItems!");
            }
        }
        service.save(fullOrderDTO);
        return "Success!";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String update(@PathVariable("id") Integer id, @Valid @RequestBody FullOrderDTO fullOrderDTO) {
        service.update(id, fullOrderDTO);
        return "Success!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return "Delete successful!";
    }



    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public List<FullOrderDTO> myOrders(Authentication authentication){
        BookStoreUserDetails userDetails = (BookStoreUserDetails) authentication.getPrincipal();
        return service.findAllByCostumer(userDetails.getUser().getCostumer());
    }

    @PostMapping("/my")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String addMyOrder(Authentication authentication, @Valid @RequestBody MyFullOrderDTO myOrderDTO) throws UnexpectedIdException { //MyOrderDTO because I add an order for me
        if (myOrderDTO.getId() != null) {
            throw new UnexpectedIdException("Id is unexpected here!");
        }
        BookStoreUserDetails userDetails = (BookStoreUserDetails) authentication.getPrincipal();
        Costumer costumer = userDetails.getUser().getCostumer();
        service.saveForCustomer(myOrderDTO, costumer);
        return "Success!";
    }

}
