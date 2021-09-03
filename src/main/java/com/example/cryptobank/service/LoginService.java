package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    private RootRepository rootRepository;
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public LoginService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New LoginService");
    }

    public Admin loginAdmin(String username, String password) {
        Admin attemptToLogin = new Admin(username, password);
        Admin adminInDatabase = rootRepository.findAdminByUsername(username);
        String token = null;

        if(adminInDatabase != null && attemptToLogin.getUsername().equals(adminInDatabase.getUsername())) {
            String salt = adminInDatabase.getSalt();
            String hashedPassword = HashHelper.hash(attemptToLogin.getPassword(), salt, PepperService.getPepper());

            if(authenticate(adminInDatabase.getPassword(), hashedPassword)) {
                attemptToLogin.setPassword(hashedPassword);
                attemptToLogin.setSalt(salt);
                token = UUID.randomUUID().toString();
                rootRepository.insertTokenByAdminUsername(username, token);

                attemptToLogin.setToken(token);

                return attemptToLogin;
            }
        }
        return attemptToLogin;
    }

    public boolean authenticate(String hashInDatabase, String hashedPassword) {
        return hashInDatabase.equals(hashedPassword);
    }

    public Customer loginCustomer(String username, String password) {
        Customer attemptToLogin = new Customer(username, password);
        Customer customerInDatabase = rootRepository.findCustomerByUsername(username);
        String token = null;

        if (customerInDatabase != null && attemptToLogin.getUsername().equals(customerInDatabase.getUsername())) {
            String salt = customerInDatabase.getSalt();
            String hashedPassword = HashHelper.hash(attemptToLogin.getPassword(), salt, PepperService.getPepper());

            if (authenticate(customerInDatabase.getPassword(), hashedPassword)) {
                token = UUID.randomUUID().toString();
                rootRepository.insertTokenByCustomerUsername(username, token);
                customerInDatabase.setToken(token);
                return customerInDatabase;
            }
        }
        return attemptToLogin;
    }
}