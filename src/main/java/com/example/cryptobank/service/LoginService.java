package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.dto.CustomerDto;
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

    public CustomerDto loginCustomer(CustomerDto customerDto) {
        String token = null;
        CustomerDto attemptToLogin = new CustomerDto(customerDto.getUsername(), customerDto.getPassword());
        attemptToLogin.setToken(token);

        Customer customerInDatabase = rootRepository.findCustomerByUsername(customerDto.getUsername());

        if (customerInDatabase != null && attemptToLogin.getUsername().equals(customerInDatabase.getUsername())) {
            String salt = customerInDatabase.getSalt();
            String hashedPassword = HashHelper.hash(attemptToLogin.getPassword(), salt, PepperService.getPepper());

            if (authenticate(customerInDatabase.getPassword(), hashedPassword)) {
                token = UUID.randomUUID().toString();
                rootRepository.insertTokenByCustomerUsername(customerDto.getUsername(), token);
                attemptToLogin.setToken(token);
                attemptToLogin.setFirstName(customerDto.getFirstName());
                attemptToLogin.setIban(customerInDatabase.getBankAccount().getIban());
                return attemptToLogin;
            }
        }
        return attemptToLogin;
    }
}