package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class TransactionService {

    private RootRepository rootRepository;
    private BankAccountService bankAccountService;
    private Bank bank = Bank.getInstance();
    private final double TRANSACTION_RATE = 0.03;   // TODO transaction rate instelbaar maken

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New TransactionService");
    }


    public Transaction findByTransactionId(int transactionId) {
        return rootRepository.findByTransactionId(transactionId);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return rootRepository.save(transaction);
    }

    /**
     *  Opzet methode completeTransaction voor koop van platform. Moet nog opgesplitst worden.
     *  Hier vanuit een doorgegeven Order geredeneerd.
     */
    public Transaction completeTransactionFromBank(Order orderToProcess) {

        Transaction transactionToComplete = new Transaction();
        transactionToComplete.setDateTimeTransaction(LocalDateTime.now());
        transactionToComplete.setAsset(orderToProcess.getAsset());

        double assetAmount = orderToProcess.getAssetAmount();
        double assetPrice = orderToProcess.getDesiredPrice(); // voor nu even, getCurrentAssetPrice nodig
        transactionToComplete.setAssetAmount(assetAmount);
        transactionToComplete.setAssetPrice(assetPrice);

        BankAccount buyerAccount = orderToProcess.getBankAccount();
        BankAccount sellerAccount = bank.getBankAccount();
        transactionToComplete.setBuyerAccount(buyerAccount);
        transactionToComplete.setSellerAccount(sellerAccount);

        double assetCost = assetPrice * assetAmount;
        double transactionCost = assetCost * TRANSACTION_RATE;  // Voor nu, instelbare transaction rate nodig
        transactionToComplete.setTransactionCost(transactionCost);

        double totalCost = assetCost + transactionCost;

        validateCreditLimit(buyerAccount, totalCost);

        rootRepository.withdraw(buyerAccount.getIban(), totalCost);
        rootRepository.deposit(sellerAccount.getIban(), totalCost);

        rootRepository.updateAssetAmountNegative(transactionToComplete);
        rootRepository.updateAssetAmountPositive(transactionToComplete);

        rootRepository.save(transactionToComplete);

        return transactionToComplete;
    }

    private double calculateAssetCost(double assetPrice, double assetAmount) {
        return assetPrice * assetAmount;
    }

    private double calculateTransactionCost(double assetCost) {
        return assetCost * TRANSACTION_RATE;
    }

    private double calculateTransactionCostSplit(double assetCost, double transactionRate) {
        return assetCost * (transactionRate / 2);
    }

    private boolean validateCreditLimit(BankAccount bankAccount, double amount) {
        if (amount > bankAccount.getBalance()) {
            logger.info("Insufficient funds!");
            return false;
        } else {
            return true;
        }
    }
}