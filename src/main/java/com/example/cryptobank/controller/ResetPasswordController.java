package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Mail;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.service.AdminService;
import com.example.cryptobank.service.AuthenticationService;
import com.example.cryptobank.service.CustomerService;
import com.example.cryptobank.service.SendMailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forgot")
public class ResetPasswordController {
    private final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    private final static String URL = "http://localhost:8080/api/forgot/reset?token=";
    private final static String MAIL_SUBJECT = "reset password";
    private final static String MAIL_MESSAGE = "Click this link to reset your password. \n" +
            "After 30 minutes, this link will expire.\n\n";



    private AuthenticationService authenticationService;
    private CustomerService customerService;
    private AdminService adminService;
    private SendMailServiceImpl sendMailService;


    @Autowired
    public ResetPasswordController(AuthenticationService authenticationService, CustomerService customerService,
                                   AdminService adminService, SendMailServiceImpl sendMailService) {
         this.authenticationService = authenticationService;
         this.customerService = customerService;
         this.adminService = adminService;
         this.sendMailService = sendMailService;
        logger.info("New ResetPasswordController");
    }

    @PostMapping("/password")
    ResponseEntity<?> sendResetPasswordMail(@RequestParam String email) {
        logger.info("reset password mail aangeroepen");
        CustomerDto customer = customerService.findCustomerByEmail(email);
       if (customer != null) {
           String urlToken = URL + customer.getRefreshToken();
           String message = MAIL_MESSAGE + urlToken;
           Mail mail = new Mail(customer.getEmail(), MAIL_SUBJECT, message);
           sendMailService.sendMail(mail);
            return new ResponseEntity<String>(mail.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("mail not known", HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/reset")
    ResponseEntity<?> resetPassword(@RequestParam String token) {
        logger.info("reset password endpoint aangeroepen");
        token = "Bearer " + token;
        CustomerDto customer = customerService.authenticate(token);
        if (customer != null) {
            return new ResponseEntity<String>("vet lekker aan het werk jij", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("mail not known", HttpStatus.I_AM_A_TEAPOT);
        }
    }

}