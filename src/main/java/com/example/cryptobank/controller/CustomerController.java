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

    // ik heb gekozen om de camelcase hier even weg te laten ivm het aanmaken van customers in postman
    // anders word ik gek. Mocht dit echt not done zijn, dan moet er een andere oplossing zijn.
    // is dat niet het geval: bite me :)
    @PutMapping("/register")
    public Customer register(@RequestParam String username, String password,
                             String firstname, String lastname, String dateofbirth, int socialsecuritynumber,
                             String street, String zipcode, int housenumber, String addition) {
        Customer customerToRegister = customerService.register(
                username, password, firstname, lastname, LocalDate.parse(dateofbirth), socialsecuritynumber,
                street, zipcode, housenumber, addition);
        logger.info("Customer registratie aangeroepen");
        return customerToRegister;
    }


    // TODO: 23/08/2021 Graag aan Huub vragen of we een customer ook wat slimmer kunnen aanmaken
    // dit kost ons iedere keer echt veel type werk en is daarmee extra foutgevoelig
}
