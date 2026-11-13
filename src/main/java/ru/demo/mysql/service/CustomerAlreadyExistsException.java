package ru.demo.mysql.service;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String email) {
        super("A customer with email '" + email + "' already exists");
    }
}
