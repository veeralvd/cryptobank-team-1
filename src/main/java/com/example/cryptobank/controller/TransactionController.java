package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Order;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.dto.TransactionDto;
import com.example.cryptobank.service.CustomerService;
import com.example.cryptobank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private TransactionService transactionService;
    private CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionService transactionService, CustomerService customerService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
        logger.info("New TransactionController");
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> findByTransactionId(@RequestParam int transactionId, @RequestHeader("Authorization") String accessToken) {
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        TransactionDto transactionDto = transactionService.findByTransactionId(transactionId);
        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }

   /* @GetMapping("/transactions/iban")
    public ArrayList<Transaction> getAllByIban(@RequestParam String iban) {
        return transactionService.getAllByIban(iban);
    }*/

    @PostMapping(value = "/transactions/complete", produces = "application/json")
    public ResponseEntity<?> completeTransactionFromBank(@RequestBody Order orderToProcess, @RequestHeader("Authorization") String accessToken) {
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        TransactionDto transactionDto = transactionService.completeTransaction(orderToProcess);
        if (transactionDto == null) {
            return new ResponseEntity<>("Failed to save transaction", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }

}