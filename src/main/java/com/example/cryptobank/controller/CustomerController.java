package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                             String street, String zipcode, int housenumber, String addition, String city) {
        Customer customerToRegister = customerService.register(
                username, password, firstname, lastname, LocalDate.parse(dateofbirth), socialsecuritynumber,
                street, zipcode, housenumber, addition, city);
        logger.info("Customer registratie aangeroepen");
        return customerToRegister;
    }

/*    @PutMapping(value = "/registerTwee", produces = "application/json")
    public Customer registerTwee(@RequestBody Customer customer) {
        logger.info(customer.toString());
        Customer customerToRegister = customerService.registerTwee(customer);
        return customerToRegister;
    }*/

    @PutMapping(value = "/registerTwee", produces = "application/json")
    public int registerTwee(@RequestBody Customer customer) {
        logger.info(customer.toString());
        Customer customerToRegister = customerService.registerTwee(customer);
        if(customerToRegister.getSalt()== null) {
            return new ResponseEntity<String>(HttpStatus.I_AM_A_TEAPOT).getStatusCodeValue();
        } else {
            return new ResponseEntity<String>(HttpStatus.CREATED).getStatusCodeValue();
        }
    }

    @PutMapping("/login")
    public Customer login(@RequestParam String username, String password) {
        Customer customerToLogin = customerService.login(username, password);
        logger.info("login customer aangeroepen");
        return customerToLogin;
    }


    // TODO: 23/08/2021 Graag aan Huub vragen of we een customer ook wat slimmer kunnen aanmaken
    // dit kost ons iedere keer echt veel type werk en is daarmee extra foutgevoelig
}
