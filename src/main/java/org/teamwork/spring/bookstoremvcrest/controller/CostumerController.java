package org.teamwork.spring.bookstoremvcrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.teamwork.spring.bookstoremvcrest.exceptions.NotFoundException;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
import org.teamwork.spring.bookstoremvcrest.model.dto.CostumerDTO;
import org.teamwork.spring.bookstoremvcrest.security.details.BookStoreUserDetails;
import org.teamwork.spring.bookstoremvcrest.service.impl.CostumerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/costumers")
public class CostumerController {
    @Autowired
    private CostumerServiceImpl costumerService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<CostumerDTO> findAll() {
        return costumerService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public CostumerDTO findById(@PathVariable("id") Integer id) throws NotFoundException {
        CostumerDTO costumerDTO = costumerService.findByKey(id);
        if (costumerDTO == null) {
            throw new NotFoundException();
        }
        return costumerService.findByKey(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody CostumerDTO costumerDTO) throws UnexpectedIdException {
        if (costumerDTO.getId() != null) {
            throw new UnexpectedIdException();
        }
        costumerService.save(costumerDTO);
        return "Save success!";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String update(@Valid @RequestBody CostumerDTO costumerDTO, @PathVariable("id") Integer id) {
        costumerService.update(id, costumerDTO);
        return "Update successful!";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable("id") Integer id) {
        costumerService.delete(id);
        return "Success!";
    }


    //Special for user
    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')") //this role is needed because only user can be a costumer!
    @ResponseStatus(HttpStatus.OK)
    public CostumerDTO showMe(Authentication authentication){
        BookStoreUserDetails userDetails = (BookStoreUserDetails) authentication.getPrincipal();
        return costumerService.findByKey(userDetails.getUser().getCostumer().getId());
    }

    @PatchMapping("/me")
//    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public String updateMe(Authentication authentication, @Valid @RequestBody CostumerDTO costumerDTO) {
        BookStoreUserDetails user = (BookStoreUserDetails) authentication.getPrincipal();
        costumerService.update(user.getUser().getCostumer().getId(), costumerDTO);
        return "Success!";
    }
}
