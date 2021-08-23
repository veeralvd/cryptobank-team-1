package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CustomerController {

    private CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        logger.info("New CustomerController");
    }

    @PutMapping("/register")
    public Customer register(@RequestParam String username, String password,
                             String firstName, String lastName, LocalDate dateOfBirth, int socialSecurityNumber,
                             String street, String zipcode, int houseNumber, String addition) {
        // TODO: 23/08/2021 methode invoegen
        logger.info("Customer registratie aangeroepen");
        return null;
    }


    // TODO: 23/08/2021 Graag aan Huub vragen of we een customer ook wat slimmer kunnen aanmaken
    // dit kost ons iedere keer echt veel type werk en is daarmee extra foutgevoelig
}
