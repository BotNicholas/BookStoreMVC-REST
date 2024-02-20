package org.teamwork.spring.bookstoremvcrest.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class LightOrderItemDTO implements DefaultDTO {
    private Integer id;
    @NotEmpty(message = "Specify the order item!")
    private Integer bookId;
    @Min(value = 1, message = "Minipal agreed price is 1")
    private Double itemAgreedPrice;
    private String itemComment;

    public LightOrderItemDTO() {
    }

    public LightOrderItemDTO(Integer bookId, Double itemAgreedPrice, String itemComment) {
        this.bookId = bookId;
        this.itemAgreedPrice = itemAgreedPrice;
        this.itemComment = itemComment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBook() {
        return bookId;
    }

    public void setBook(Integer bookId) {
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
                ", bookId=" + bookId +
                ", itemAgreedPrice=" + itemAgreedPrice +
                ", itemComment='" + itemComment + '\'' +
                '}';
    }
}
