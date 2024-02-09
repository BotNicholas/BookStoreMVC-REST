package org.teamwork.spring.bookstoremvcrest.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemDTO {
    private Integer id;
    @NotNull(message = "Specify the order!")
    private Integer orderId;
    @NotNull(message = "Specify the order item!")
    private Integer bookId;
    @Min(value = 1, message = "Minipal agreed price is 1")
    private Double itemAgreedPrice;
    private String itemComment;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Integer orderId, Integer bookId, Double itemAgreedPrice, String itemComment) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.itemAgreedPrice = itemAgreedPrice;
        this.itemComment = itemComment;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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
                ", orderId=" + orderId +
                ", bookId=" + bookId +
                ", itemAgreedPrice=" + itemAgreedPrice +
                ", itemComment='" + itemComment + '\'' +
                '}';
    }
}
