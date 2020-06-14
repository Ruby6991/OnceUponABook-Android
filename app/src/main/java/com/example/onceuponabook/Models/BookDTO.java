package com.example.onceuponabook.Models;

import com.example.onceuponabook.EnumClasses.BookFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BookDTO implements Serializable {

    private int id;
    private long isbn;
    private String publisher;
    private Date publicationDate;
    private String author;
    private String title;
    private double price;
    private String category;

    private BookFormat format;

    private String description;
    private String imagePath;
    private int qtyInStock;

    private List<UserDTO> users;
    private List<OrderBookDTO> orderedBooks;

    public BookDTO() {
    }

    public BookDTO(String category, String image) {
        this.category = category;
        this.imagePath = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BookFormat getFormat() {
        return format;
    }

    public void setFormat(BookFormat format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public List<OrderBookDTO> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(List<OrderBookDTO> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }
}
