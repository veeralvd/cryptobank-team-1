package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Bank;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Order;
import com.example.cryptobank.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionBuyFromBankService extends TransactionAbstractClass {

    private RootRepository rootRepository;
    private final Logger logger = LoggerFactory.getLogger(TransactionBuyFromBankService.class);

    @Autowired
    public TransactionBuyFromBankService(RootRepository rootRepository) {
        super(rootRepository);
        logger.info("New TransactionBuyFromBankService");
    }

    @Override
    double calculateAmountToPay(Order orderToProcess) {
        // Hier: totaalbedrag te betalen door koper/klant aan verkoper/bank >> calculate amountToPay
        double totalCost = super.calculateAssetCost(orderToProcess) + super.calculateTransactionCost(orderToProcess);
        return totalCost;
    }
}