package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Bank;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private RootRepository rootRepository;
    private BankAccountService bankAccountService;

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
     *  Opzet methode completeTransaction. Moet nog opgesplitst worden.
     */
    public void completeTransaction(Transaction transaction) {

        double amountToPay;
        double amountToReceive;
        double assetCost = calculateAssetCost(transaction.getAssetPrice(), transaction.getAssetAmount());
        double transactionCost;

        if (transaction.getBuyerAccount().getIban().equals(Bank.getBankIban())) {
            // als het om een aankoop van de bank gaat:
            transactionCost = calculateTransactionCost(assetCost, transaction.getTransactionCost());
            amountToPay = assetCost + transactionCost;
            amountToReceive = amountToPay;
        } else {
            // als het om een koop tussen klanten gaat:
            transactionCost = calculateTransactionCostSplit(assetCost, transaction.getTransactionCost());
            amountToPay = assetCost + transactionCost;
            amountToReceive = assetCost - transactionCost;
            bankAccountService.deposit(Bank.getBankIban(), (transactionCost * 2));
        }

        validateCreditLimit(transaction.getBuyerAccount(), amountToPay);

        bankAccountService.withdraw(transaction.getBuyerAccount().getIban(), amountToPay);
        bankAccountService.deposit(transaction.getSellerAccount().getIban(), amountToReceive);

        // JdbcPortfolioDao:
        // updatePortfolioStatementPositive (Portfolio portfolio, Customer customer, Order order, Connection connection)
        // updatePortfolioStatementNegative (Portfolio portfolio, Customer customer, Order order, Connection connection)
        // transaction.getBuyerAccount().getIban()
        // hele customer voor nodig?
        // Transaction ipv order

    }

    private double calculateAssetCost(double assetPrice, double assetAmount) {
        return assetPrice * assetAmount;
    }

    private double calculateTransactionCost(double assetCost, double transactionRate) {
        return assetCost * transactionRate;
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