package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
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

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
        logger.info("New TransactionController");
    }

    /**
     * Endpoint om kooporder voor asset te plaatsen. De koopknop moet het betreffende asset meegeven, dus evt
     * verplaatsen naar AssetController. Ook moet ervoor gezorgd worden dat Iban koper en Iban verkoper meegegeven
     * worden.
     * @return boolean status (voor nu, zie demo restfullwebserver voor ResponseEntity)
     */
    @PostMapping("/buyAsset")
    // public int buyAsset(@RequestParam String ibanBuyer, String ibanSeller, Asset asset, double amount) {
    public int buyAsset(@RequestBody Transaction transaction) {
        Transaction orderToSave = transactionService.placeOrder(transaction);
        if (orderToSave.getBuyerAccount().getIban() == null) {
            return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST).getStatusCodeValue();
        } else {
            return new ResponseEntity<String>(HttpStatus.OK).getStatusCodeValue();
        }
    }


    @GetMapping("/orders") // /{iban}
    Transaction FindOrdersByIban(@RequestParam String iban) {
        return null;
    }

}