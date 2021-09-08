package com.knight.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomerDto {

    private String username;
    private String password;
    private String token;
    private String firstName;
    private String iban;
    private final Logger logger = LoggerFactory.getLogger(CustomerDto.class);

    public CustomerDto() {
        logger.info("New CustomerDto");
    }

    public CustomerDto(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public CustomerDto(String username, String password, String token, String firstName, String iban) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.firstName = firstName;
        this.iban = iban;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}