package org.teamwork.spring.bookstoremvcrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapper;
import org.teamwork.spring.bookstoremvcrest.model.Order;
import org.teamwork.spring.bookstoremvcrest.model.dto.OrderDTO;
import org.teamwork.spring.bookstoremvcrest.repository.OrderRepository;
import org.teamwork.spring.bookstoremvcrest.service.DefaultService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements DefaultService<OrderDTO, Order, Integer> {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AbstractMapper mapper;
    @Override
    public List<OrderDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS = orders.stream().map(order -> mapper.toDTO(order, OrderDTO.class)).collect(Collectors.toList());

        return orderDTOS;
    }

    @Override
    public OrderDTO findByKey(Integer key) {
        return mapper.toDTO(orderRepository.findById(key), OrderDTO.class);
    }

    @Override
    public OrderDTO save(OrderDTO obj) {
        Order order = orderRepository.save(mapper.toEntity(obj, Order.class));

        return mapper.toDTO(order, OrderDTO.class);
    }

    @Override
    public OrderDTO update(Integer key, OrderDTO obj) {
        Order order = orderRepository.findById(key).orElse(new Order());

        order.setOrderDate(obj.getOrderDate());
        order.setOrderValue(obj.getOrderValue());

        return mapper.toDTO(order, OrderDTO.class);
    }

    @Override
    public void delete(Integer key) {
        orderRepository.deleteById(key);
    }
}
