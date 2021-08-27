package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private RootRepository rootRepository;
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    public RegistrationService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New RegistrationService");
    }

    public Admin register(String username, String password) {
        Admin attemptToRegister = new Admin(username,password);
        Admin adminInDatabase = rootRepository.findByUsername(username);

        if(adminInDatabase == null || attemptToRegister.getUsername().equals(adminInDatabase.getUsername())) {
            String salt = new Saltmaker().generateSalt();attemptToRegister.setPassword(HashHelper.hash(password, salt, PepperService.getPepper()));
            attemptToRegister.setSalt(salt);
            Admin registeredAdmin = rootRepository.save(attemptToRegister);
            return registeredAdmin;
        }
        return attemptToRegister;
    }

    public Customer register(Customer customer) {

        return null;
    }

}
