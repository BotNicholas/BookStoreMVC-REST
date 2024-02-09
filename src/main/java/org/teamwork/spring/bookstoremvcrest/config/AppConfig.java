package org.teamwork.spring.bookstoremvcrest.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.teamwork.spring.bookstoremvcrest.model.*;
import org.teamwork.spring.bookstoremvcrest.model.dto.AuthorDTO;
import org.teamwork.spring.bookstoremvcrest.model.dto.CustomerDTO;
import org.teamwork.spring.bookstoremvcrest.model.dto.OrderDTO;
import org.teamwork.spring.bookstoremvcrest.repository.BookRepository;
import org.teamwork.spring.bookstoremvcrest.repository.OrderItemRepository;
import org.teamwork.spring.bookstoremvcrest.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan("org.teamwork.spring.bookstoremvcrest")
public class AppConfig {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Bean
//    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        addCustomMappings(mapper);
        return mapper;
    }

    private void addCustomMappings(ModelMapper mapper){
        authorsCustomMapper(mapper);
        customerCustomMapper(mapper);
        orderCustomMapper(mapper);
    }

    private void authorsCustomMapper(ModelMapper mapper){
        //To DTO
        mapper.typeMap(Author.class, AuthorDTO.class).addMappings(mapping -> mapping.using((MappingContext<List<Book>, List<Integer>> context) -> {
            List<Book> books = context.getSource();
            return books.stream().map(book -> book.getId()).collect(Collectors.toList());
        }).map(Author::getBooks, AuthorDTO::setBooks));

        //From DTO
        mapper.typeMap(AuthorDTO.class, Author.class).addMappings(mapping -> mapping.using((MappingContext<List<Integer>, List<Book>> context) -> {
            List<Integer> booksId = context.getSource();
            return booksId.stream().map(bookId -> bookRepository.findById(bookId).orElse(null)).collect(Collectors.toList());
        }).map(AuthorDTO::getBooks, Author::setBooks));
    }

    private void customerCustomMapper(ModelMapper mapper){
        //To DTO
        mapper.typeMap(Customer.class, CustomerDTO.class).addMappings(mapping -> mapping.using((MappingContext<List<Order>, List<Integer>> context) -> {
            List<Order> orders = context.getSource();
            return orders.stream().map(order -> order.getId()).collect(Collectors.toList());
        }).map(Customer::getOrders, CustomerDTO::setOrders));

        //From DTO
        mapper.typeMap(CustomerDTO.class, Customer.class).addMappings(mapping -> mapping.using((MappingContext<List<Integer>, List<Order>> context)->{
            List<Integer> ordersId = context.getSource();
            return ordersId.stream().map(orderId -> orderRepository.findById(orderId).orElse(null)).collect(Collectors.toList());
        }).map(CustomerDTO::getOrders, Customer::setOrders));
    }

    private void orderCustomMapper(ModelMapper mapper){
        //To DTO
        mapper.typeMap(Order.class, OrderDTO.class).addMappings(mapping -> mapping.using((MappingContext<List<OrderItem>, List<Integer>> context) -> {
            List<OrderItem> orderItems = context.getSource();
            return orderItems.stream().map(orderItem -> orderItem.getId()).collect(Collectors.toList());
        }).map(Order::getItemList, OrderDTO::setItemList));

        //From DTO
        mapper.typeMap(OrderDTO.class, Order.class).addMappings(mapping -> mapping.using((MappingContext<List<Integer>, List<OrderItem>> context) -> {
            List<Integer> orderItemsId = context.getSource();
            return orderItemsId.stream().map(orderItemId -> orderItemRepository.findById(orderItemId).orElse(null)).collect(Collectors.toList());
        }).map(OrderDTO::getItemList, Order::setItemList));
    }
}
