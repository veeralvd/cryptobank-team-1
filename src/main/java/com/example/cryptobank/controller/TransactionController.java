package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Order;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
        logger.info("New TransactionController");
    }

    @GetMapping("/transactions")
    public Transaction findByTransactionId(@RequestParam int transactionId) {
        return transactionService.findByTransactionId(transactionId);
    }

   /* @GetMapping("/transactions/{transactionid}")
    public Transaction findByTransactionId(@PathVariable("transactionid") int transactionId) {
        return transactionService.findByTransactionId(transactionId);
    }*/

   /* @GetMapping("/orders/iban")
    public ArrayList<Transaction> getAllByIban(@RequestParam String iban) {
        return transactionService.getAllByIban(iban);
    }*/

    /**
     * Endpoint voor completeTransaction. -> vanuit order?
     */
    */
    @PutMapping("/transactions/complete")
    public int completeTransaction(@RequestBody Transaction transaction) {
        Transaction transactionToSave = transactionService.completeTransaction(transaction);
        if (transactionToSave.getTransactionId() == null) {
            return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST).getStatusCodeValue();
        } else {
            return new ResponseEntity<String>(HttpStatus.OK).getStatusCodeValue();
        }
    }

}