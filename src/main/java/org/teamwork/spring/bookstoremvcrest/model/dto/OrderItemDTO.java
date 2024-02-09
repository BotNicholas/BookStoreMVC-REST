package org.teamwork.spring.bookstoremvcrest.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemDTO implements DefaultDTO {
    private Integer id;
    @NotNull(message = "Specify the order!")
    private OrderDTO order;
    @NotNull(message = "Specify the order item!")
    private BookDTO book;
    @Min(value = 1, message = "Minipal agreed price is 1")
    private Double itemAgreedPrice;
    private String itemComment;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderDTO order, BookDTO book, Double itemAgreedPrice, String itemComment) {
        this.order = order;
        this.book = book;
        this.itemAgreedPrice = itemAgreedPrice;
        this.itemComment = itemComment;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public Double getItemAgreedPrice() {
        return itemAgreedPrice;
    }

    public void setItemAgreedPrice(Double itemAgreedPrice) {
        this.itemAgreedPrice = itemAgreedPrice;
    }

    public String getItemComment() {
        return itemComment;
    }

    public void setItemComment(String itemComment) {
        this.itemComment = itemComment;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + id +
                ", orderId=" + order +
                ", bookId=" + book +
                ", itemAgreedPrice=" + itemAgreedPrice +
                ", itemComment='" + itemComment + '\'' +
                '}';
    }
}
