package org.teamwork.spring.bookstoremvcrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapper;
import org.teamwork.spring.bookstoremvcrest.model.Costumer;
import org.teamwork.spring.bookstoremvcrest.model.OrderItem;
import org.teamwork.spring.bookstoremvcrest.model.dto.OrderItemDTO;
import org.teamwork.spring.bookstoremvcrest.repository.BookRepository;
import org.teamwork.spring.bookstoremvcrest.repository.OrderItemRepository;
import org.teamwork.spring.bookstoremvcrest.repository.OrderRepository;
import org.teamwork.spring.bookstoremvcrest.service.DefaultService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements DefaultService<OrderItemDTO, OrderItem, Integer> {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AbstractMapper mapper;
    @Override
    public List<OrderItemDTO> findAll() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<OrderItemDTO> orderItemDTOS = orderItems.stream().map(orderItem -> mapper.toDTO(orderItem, OrderItemDTO.class)).collect(Collectors.toList());
        return orderItemDTOS;
    }

    @Override
    public OrderItemDTO findByKey(Integer key) {
        return mapper.toDTO(orderItemRepository.findById(key), OrderItemDTO.class);
    }

    public List<OrderItemDTO> findAllByCostumer(Costumer costumer){
        List<OrderItem> orderItems = orderItemRepository.findAllByOrder_Costumer(costumer);
        List<OrderItemDTO> orderItemDTOS = orderItems.stream().map(orderItem -> mapper.toDTO(orderItem, OrderItemDTO.class)).collect(Collectors.toList());
        return orderItemDTOS;
    }

    @Override
    public OrderItemDTO save(OrderItemDTO obj) {
        OrderItem orderItem = orderItemRepository.save(mapper.toEntity(obj, OrderItem.class));
        return mapper.toDTO(orderItem, OrderItemDTO.class);
    }

    @Override
    public OrderItemDTO update(Integer key, OrderItemDTO obj) {
        OrderItem orderItem = mapper.toEntity(obj, OrderItem.class);
        orderItem.setId(key);
        orderItemRepository.save(orderItem);
        return mapper.toDTO(orderItem, OrderItemDTO.class);
    }

    @Override
    public void delete(Integer key) {
        orderItemRepository.deleteById(key);
    }
}
