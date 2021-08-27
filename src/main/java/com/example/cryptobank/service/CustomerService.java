package com.example.cryptobank.service;

import com.example.cryptobank.database.CustomerDAO;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Address;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import org.apache.tomcat.util.buf.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    // TODO: 25/08/2021 Mark: customerDAo refactoren naar rootrepository met bijbehorende methoden 
    private CustomerDAO customerDAO;
    private RootRepository rootRepository;
    private RegistrationService registrationService;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerDAO customerDAO, RootRepository rootRepository, RegistrationService registrationService) {
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
        String salt = new Saltmaker().generateSalt();
        if (checkIfCustomerCanBeRegistered(customerToRegister.getUsername())) {
            customerToRegister.setPassword(HashHelper.hash(customerToRegister.getPassword(),
                    salt,
                    PepperService.getPepper()));
            customerToRegister.setSalt(salt);
            customerToRegister.setBankAccount(new BankAccount());
            Customer customerRegistered = customerDAO.save(customerToRegister);
            return customerRegistered;
        }
        return customerToRegister;
    }

    public Customer findByUsername(String username){
        //return rootRepository.getUserByUsername(username);
        return null;
    }

    public Customer login(String username, String password) {
        Customer attemptToLogin = new Customer(username, password);
        Customer customerInDatabase = findByUsername(username);

        if (attemptToLogin.getUsername().equals(customerInDatabase.getUsername())) {
            String salt = customerInDatabase.getSalt();
            String hashedPassword = HashHelper.hash(attemptToLogin.getPassword(), salt, PepperService.getPepper());

            if (authenticate(customerInDatabase.getPassword(), hashedPassword)) {
                attemptToLogin.setPassword(hashedPassword);
                attemptToLogin.setSalt(salt);
                return attemptToLogin;
            }
        }
        return attemptToLogin;
    }

    public boolean authenticate(String hashInDatabase, String hashedPassword) {
        return hashInDatabase.equals(hashedPassword);
    }

}
