package com.example.cryptobank.service;

import com.example.cryptobank.database.CustomerDAO;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Address;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    // TODO: 25/08/2021 Mark: customerDAo refactoren naar rootrepository met bijbehorende methoden
    private LoginService loginService;
    private CustomerDAO customerDAO;
    private RootRepository rootRepository;
    private RegistrationService registrationService;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(LoginService loginService, CustomerDAO customerDAO, RootRepository rootRepository, RegistrationService registrationService) {
        this.loginService = loginService;
        this.customerDAO = customerDAO;
        this.rootRepository = rootRepository;
        this.registrationService = registrationService;
        logger.info("New CustomerService");
    }



    public Customer register(String username, String password,
                             String firstName, String lastName, LocalDate dateOfBirth, int socialSecurityNumber,
                             String street, String zipcode, int houseNumber, String addition, String city) {
        Customer customerToRegister = registrationService.registerCustomer(username, password, firstName, lastName,
                dateOfBirth, socialSecurityNumber, street, zipcode, houseNumber, addition, city);
        return customerToRegister;
    }

    public Customer registerTwee(Customer customerToRegister) {
        return registrationService.registerTwee(customerToRegister);
    }

    public Customer login(String username, String password) {
        Customer attemptToLogin = loginService.loginCustomer(username, password);
        return attemptToLogin;
    }
}
