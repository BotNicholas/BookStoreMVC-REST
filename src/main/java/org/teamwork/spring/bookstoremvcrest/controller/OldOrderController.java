package org.teamwork.spring.bookstoremvcrest.controller;//package org.teamwork.spring.bookstoremvcrest.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;
//import org.teamwork.spring.bookstoremvcrest.model.dto.orders.OrderDTO;
//import org.teamwork.spring.bookstoremvcrest.security.model.dto.MyOrderDTO;
//import org.teamwork.spring.bookstoremvcrest.security.userdetails.BookStoreUserDetails;
//import org.teamwork.spring.bookstoremvcrest.service.impl.OrderServiceImpl;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//    @Autowired
//    private OrderServiceImpl orderService;
//
//    @GetMapping("")
//    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderDTO> findAll() {
//        return orderService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
//    @ResponseStatus(HttpStatus.OK)
//    public OrderDTO findById(@PathVariable("id") Integer id) {
//        return orderService.findByKey(id);
//    }
//
//    @PostMapping()
//    @PreAuthorize("hasRole('ADMIN')")
//    @ResponseStatus(HttpStatus.OK)
//    public String save(@RequestBody OrderDTO orderDTO) throws UnexpectedIdException {
//        if (orderDTO.getId() != null) {
//            throw new UnexpectedIdException();
//        }
//        orderService.save(orderDTO);
//        return "Save success!";
//    }
//
//    @PatchMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    @ResponseStatus(HttpStatus.OK)
//    public String update(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("id") Integer id) {
//        orderService.update(id, orderDTO);
//        return "Update successful!";
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    @ResponseStatus(HttpStatus.OK)
//    public String delete(@PathVariable("id") Integer id) {
//        orderService.delete(id);
//        return "Delete successful!";
//    }
//
//    @GetMapping("/my")
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderDTO> myOrders(Authentication authentication){
//        BookStoreUserDetails userDetails = (BookStoreUserDetails) authentication.getPrincipal();
//        return orderService.findAllByCostumer(userDetails.getUser().getCostumer());
//    }
//
//    @PostMapping("/my")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public String addMyOrder(Authentication authentication, @Valid @RequestBody MyOrderDTO myOrderDTO) throws UnexpectedIdException { //MyOrderDTO because I add an order for me
//        if (myOrderDTO.getId() != null) {
//            throw new UnexpectedIdException("Id is unexpected here!");
//        }
//        BookStoreUserDetails userDetails = (BookStoreUserDetails) authentication.getPrincipal();
//        orderService.saveForCustomer(myOrderDTO, userDetails.getUser().getCostumer());
//        return "Success!";
//    }
//}
