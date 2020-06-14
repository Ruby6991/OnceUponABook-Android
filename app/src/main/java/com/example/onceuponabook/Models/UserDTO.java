package com.example.onceuponabook.Models;

import com.example.onceuponabook.EnumClasses.UserRole;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserDTO implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
    private int phoneNo;
    private String address;
    private String password;
    private Date dateOfBirth;
    private UserRole role;

    private List<OrderDTO> orders;
    private List<BookDTO> books;
    private String jwtToken;

    public UserDTO() {
    }

    public UserDTO( String firstName, String lastName, int phoneNo, String email, String address, String password, UserRole role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.address = address;
        this.password = password;
        this.role=role;
    }

    public UserDTO(String email, String pass, String jwttoken) {
        this.email = email;
        this.password = pass;
        this.jwtToken = jwttoken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
