package com.example.onceuponabook.Models;

import java.io.Serializable;

public class OrderBookDTO implements Serializable {

    private int quantity;
    private OrderDTO order;
    private BookDTO book;

    public OrderBookDTO() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
