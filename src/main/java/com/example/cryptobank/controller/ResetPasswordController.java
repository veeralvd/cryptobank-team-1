package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Mail;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.service.AdminService;
import com.example.cryptobank.service.AuthenticationService;
import com.example.cryptobank.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forgot")
public class ResetPasswordController {
    private final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);

    private AuthenticationService authenticationService;
    private CustomerService customerService;
    private AdminService adminService;

    @Autowired
    public ResetPasswordController(AuthenticationService authenticationService, CustomerService customerService,
                                   AdminService adminService) {
         this.authenticationService = authenticationService;
         this.customerService = customerService;
         this.adminService = adminService;
        logger.info("New ResetPasswordController");
    }

    @PostMapping("/admin/login")
    ResponseEntity<?> sendResetPasswordMail(@RequestParam String email) {
        logger.info("reset password mail aangeroepen");
        CustomerDto customer = customerService.findCustomerByEmail(email);
       if (customer != null) {
            return new ResponseEntity<String>(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("verkeerd mailadres", HttpStatus.I_AM_A_TEAPOT);
        }
    }

}