package com.example.cryptobank.service;

import com.example.cryptobank.database.CustomerDAO;
import com.example.cryptobank.domain.Address;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService {

    // TODO: 25/08/2021 Mark: customerDAo refactoren naar rootrepository met bijbehorende methoden 
    private CustomerDAO customerDAO;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
        logger.info("New CustomerService");
    }

    public boolean checkIfCustomerCanBeRegistred(String username) {
        Customer customerToCheck = customerDAO.findByUsername(username);
        logger.info(String.format("customerToCheck is: %s", customerToCheck==null? "user NULL": customerToCheck.toString()));
        return customerToCheck == null;
    }

    public Customer register(String username, String password,
                             String firstName, String lastName, LocalDate dateOfBirth, int socialSecurityNumber,
                             String street, String zipcode, int houseNumber, String addition, String city) {
        Customer customerToRegister = new Customer(username, password);
        if (checkIfCustomerCanBeRegistred(username)) {
            String salt = new Saltmaker().generateSalt();
            customerToRegister.setPassword(HashHelper.hash(password, salt, PepperService.getPepper()));
            customerToRegister.setSalt(salt);
            customerToRegister.setFirstName(firstName);
            customerToRegister.setLastName(lastName);
            customerToRegister.setDateOfBirth(dateOfBirth);
            customerToRegister.setSocialSecurityNumber(socialSecurityNumber);
            customerToRegister.setAddress(new Address(street, zipcode, houseNumber, addition, city));
            customerToRegister.setBankAccount(new BankAccount());
            Customer customerRegistred = customerDAO.save(customerToRegister);
            return customerRegistred;
        }
        return customerToRegister;
    }

    public Customer registerTwee(Customer customerToRegister) {
        String salt = new Saltmaker().generateSalt();
        if (checkIfCustomerCanBeRegistred(customerToRegister.getUsername())) {
            customerToRegister.setPassword(HashHelper.hash(customerToRegister.getPassword(),
                    salt,
                    PepperService.getPepper()));
            customerToRegister.setSalt(salt);
            customerToRegister.setBankAccount(new BankAccount());
            Customer customerRegistred = customerDAO.save(customerToRegister);
            return customerRegistred;
        }
        return customerToRegister;
    }

}
