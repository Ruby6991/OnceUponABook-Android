package com.example.onceuponabook.Models;

import com.example.onceuponabook.EnumClasses.OrderStatus;
import com.example.onceuponabook.EnumClasses.PaymentMethod;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderDTO implements Serializable {

    private int id;
    private Date purchasedDate;
    private Date deliveryDate;
    private OrderStatus status;
    private double totalAmount;
    private PaymentMethod paymentMethod;
    private UserDTO user;
    private List<OrderBookDTO> orderedBooks;

    public OrderDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<OrderBookDTO> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(List<OrderBookDTO> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }
}
