package com.example.cryptobank.service;

import com.example.cryptobank.domain.Address;
import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.security.PepperService;
import com.example.cryptobank.database.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RegistrationService {
    private RootRepository rootRepository;
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    public RegistrationService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New RegistrationService");
    }

    public Admin register(String username, String password) {
        Admin attemptToRegister = new Admin(username,password);
        Admin adminInDatabase = rootRepository.findAdminByUsername(username);

        if(adminInDatabase == null || attemptToRegister.getUsername().equals(adminInDatabase.getUsername())) {
            String salt = new Saltmaker().generateSalt();attemptToRegister.setPassword(HashHelper.hash(password, salt, PepperService.getPepper()));
            String token = UUID.randomUUID().toString();
            attemptToRegister.setSalt(salt);
            attemptToRegister.setAccessToken(token);
            Admin registeredAdmin = rootRepository.saveTransaction(attemptToRegister);
            return registeredAdmin;
        }
        return attemptToRegister;
    }

    public Customer registerCustomer(String username, String password,
                                     String firstName, String lastName, LocalDate dateOfBirth, int socialSecurityNumber,
                                     String street, String zipcode, int houseNumber, String addition, String city) {
        Customer customerToRegister = new Customer(username, password);
        if (checkIfCustomerCanBeRegistered(username) && checkIfSocialSecurityNumberExists(socialSecurityNumber)) {
            String salt = new Saltmaker().generateSalt();
            customerToRegister.setPassword(HashHelper.hash(password, salt, PepperService.getPepper()));
            customerToRegister.setSalt(salt);
            customerToRegister.setFirstName(firstName);
            customerToRegister.setLastName(lastName);
            customerToRegister.setDateOfBirth(dateOfBirth);
            customerToRegister.setSocialSecurityNumber(socialSecurityNumber);
            customerToRegister.setAddress(new Address(street, zipcode, houseNumber, addition, city));
            customerToRegister.setBankAccount(new BankAccount());
            Customer customerRegistered = rootRepository.saveTransaction(customerToRegister);
            return customerRegistered;
        }
        return customerToRegister;
    }

    public Customer register(Customer customerToRegister) {
        String salt = new Saltmaker().generateSalt();
        String token = UUID.randomUUID().toString();
        if (checkIfCustomerCanBeRegistered(customerToRegister.getUsername())) {
            customerToRegister.setPassword(HashHelper.hash(customerToRegister.getPassword(),
                    salt,
                    PepperService.getPepper()));
            customerToRegister.setSalt(salt);
            String iban = IbanGenerator.generate();
            customerToRegister.setBankAccount(new BankAccount(iban));
            customerToRegister.setAccessToken(token);
            Customer customerRegistered = rootRepository.saveTransaction(customerToRegister);
            return customerRegistered;
        }
        return customerToRegister;
    }

    public boolean checkIfCustomerCanBeRegistered(String username) {
        Customer customerToCheck = rootRepository.findCustomerByUsername(username);
        // TODO: 1-9-2021 (Mark) logger is voor testen, moet later eruit
        logger.info(String.format("customerToCheck is: %s", customerToCheck==null? "user NULL": customerToCheck.toString()));
        return customerToCheck == null;
    }

    public boolean checkIfSocialSecurityNumberExists(int socialSecurityNumber){
        List<Customer> customers = rootRepository.getAllCustomers();
        boolean exists = true;
        for (Customer customer : customers){
            if (socialSecurityNumber == customer.getSocialSecurityNumber()){
                exists = false;
                logger.info("Social security number already exists.");
            }
        }
        return exists;
    }

}
