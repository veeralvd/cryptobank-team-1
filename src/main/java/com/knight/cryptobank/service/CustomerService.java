package com.knight.cryptobank.service;

import com.knight.cryptobank.database.CustomerDAO;
import com.knight.cryptobank.database.RootRepository;
import com.knight.cryptobank.domain.Customer;
import com.knight.cryptobank.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    // TODO: 25/08/2021 Mark: customerDAo refactoren naar rootrepository met bijbehorende methoden
    private LoginService loginService;
    private CustomerDAO customerDAO;
    private RootRepository rootRepository;
    private RegistrationService registrationService;
    private AuthenticationService authenticationService;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(LoginService loginService, CustomerDAO customerDAO,
                           RootRepository rootRepository, RegistrationService registrationService,
                           AuthenticationService authenticationService) {
        this.loginService = loginService;
        this.customerDAO = customerDAO;
        this.rootRepository = rootRepository;
        this.registrationService = registrationService;
        this.authenticationService = authenticationService;
        logger.info("New CustomerService");
    }


    public Customer register(Customer customerToRegister) {
        return registrationService.register(customerToRegister);
    }

//    public Customer registerCustomer(Customer customerToRegister) {
//        return registrationService.registerCustomer(customerToRegister.getUsername(), customerToRegister.getPassword(),
//                customerToRegister.getFirstName(), customerToRegister.getLastName(), customerToRegister.getDateOfBirth(),
//                customerToRegister.getSocialSecurityNumber(), customerToRegister.g);
//    }

//    public Customer login(String username, String password) {
//        Customer attemptToLogin = loginService.loginCustomer(username, password);
//        return attemptToLogin;
//    }

    public CustomerDto login(String username, String password) {
        return loginService.loginCustomer(username, password);
    }

    public CustomerDto authenticate(String token) {
        return authenticationService.authenticateCustomerToken(token);
    }

    public void refresh(CustomerDto customerToRefreshToken) {
        authenticationService.refreshCustomerToken(customerToRefreshToken);
    }
}
