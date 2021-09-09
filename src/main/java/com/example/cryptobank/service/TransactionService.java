package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Bank;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private RootRepository rootRepository;
    private BankAccountService bankAccountService;
    private Bank bank = Bank.getInstance();
    private BankAccount buyerAccount;
    private BankAccount sellerAccount;
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

    public Transaction completeTransaction(Order orderToProcess) {

        // koop van bank:
        buyerAccount = orderToProcess.getBankAccount();
        sellerAccount = bank.getBankAccount();

  /*      // verkoop aan bank
        buyerAccount = bank.getBankAccount();
        sellerAccount = orderToProcess.getBankAccount();*/

      calculateAssetCost(orderToProcess);
        calculateTransactionCost(orderToProcess);
        calculateAmountToPayReceive(orderToProcess);

        if (validateCreditLimitBuyer(orderToProcess) && validatePortfolioSellerContainsAsset(orderToProcess) && validateAssetAmountSeller(orderToProcess)) {
            updateBankAccount(orderToProcess);
            updatePortfolio(orderToProcess);
            Transaction transactionToComplete = assembleNewTransaction(orderToProcess);
            saveTransaction(transactionToComplete);
            logger.info("Transactie opgeslagen");
            return transactionToComplete;
        }
        logger.info("Transactie NIET opgeslagen");
        return null;
    }

    private double calculateAssetCost(Order orderToProcess) {
        double assetCost = orderToProcess.getDesiredPrice() * orderToProcess.getAssetAmount();
        return assetCost;
    }

    private double calculateTransactionCost(Order orderToProcess) {
        double transactionCost = calculateAssetCost(orderToProcess) * TRANSACTION_RATE;
        return transactionCost;
    }

    // hier voor alleen koop van bank: bedrag te betalen door klant = bedrag te ontvangen door bank
    private double calculateAmountToPayReceive(Order orderToProcess) {
        double assetCost = calculateAssetCost(orderToProcess);
        double transactionCost = calculateTransactionCost(orderToProcess);
        return assetCost + transactionCost;
    }

    private boolean validateCreditLimitBuyer(Order orderToProcess) {
        logger.info("Check if buyer has enough money");
        double amountToPay = calculateAmountToPayReceive(orderToProcess);
        if (amountToPay >= buyerAccount.getBalance()) {
            logger.info("Insufficient funds !!!");
            return false;
        }
        return true;
    }

    private boolean validatePortfolioSellerContainsAsset(Order orderToProcess) {
        String assetAbbr = orderToProcess.getAsset().getAbbreviation();
        String ibanSeller = sellerAccount.getIban();
        List<String> assetAbbrList = rootRepository.getAbbreviationsByIban(ibanSeller);
        for (String abbreviation: assetAbbrList) {
            if (abbreviation.equals(assetAbbr)) {
                return true;
            }
        }
        logger.info("Portfolio seller does not contain asset");
        return false;
    }

    // nog dubbel
    private boolean checkIfPortfolioBuyerContainsAsset(Order orderToProcess) {
        String assetAbbr = orderToProcess.getAsset().getAbbreviation();
        String ibanBuyer = buyerAccount.getIban();
        List<String> assetAbbrList = rootRepository.getAbbreviationsByIban(ibanBuyer);
        for (String abbreviation: assetAbbrList) {
            if (abbreviation.equals(assetAbbr)) {
                return true;
            }
        }
        logger.info("Portfolio buyer does not contain asset");
        return false;
    }

    private boolean validateAssetAmountSeller(Order orderToProcess) {
        logger.info("Check if seller has sufficient assets");
        double amountToRemove = orderToProcess.getAssetAmount();
        String assetAbbr = orderToProcess.getAsset().getAbbreviation(); // dubbele code
        String ibanSeller = sellerAccount.getIban(); // dubbele code
        double amountInPortfolio = rootRepository.getAssetAmountByIbanAndAbbr(ibanSeller, assetAbbr);
        if (amountInPortfolio >= amountToRemove) {
            return true;
        }
        logger.info("Not enough Assets to sell !!!");
        return false;
    }

    //Hier nog alleen voor aankoop van bank
    private void updateBankAccount(Order orderToProcess) {
        logger.info("Withdraw money from bankaccount buyer / deposit money to bankaccount seller");
        double amountToPayRecieve = calculateAmountToPayReceive(orderToProcess);
        rootRepository.withdraw(buyerAccount.getIban(), amountToPayRecieve);
        rootRepository.deposit(sellerAccount.getIban(), amountToPayRecieve);
    }

    private void updatePortfolio(Order orderToProcess) {
        logger.info("Remove assets from portfolio seller / add assets to portfolio buyer");
        Transaction transactionToComplete = assembleNewTransaction(orderToProcess);
        boolean portfolioBuyerContainsAsset = checkIfPortfolioBuyerContainsAsset(orderToProcess);
        if (!portfolioBuyerContainsAsset) {
            rootRepository.insertAssetIntoPortfolio(transactionToComplete);
        }
        rootRepository.updateAssetAmountNegative(transactionToComplete);
        rootRepository.updateAssetAmountPositive(transactionToComplete);
    }

    private Transaction assembleNewTransaction(Order orderToProcess) {
        Transaction transactionToComplete = new Transaction();
        transactionToComplete.setAsset(orderToProcess.getAsset());
        transactionToComplete.setAssetAmount(orderToProcess.getAssetAmount());
        transactionToComplete.setAssetPrice(orderToProcess.getDesiredPrice()); // voor nu, currentPrice nodig
        transactionToComplete.setBuyerAccount(buyerAccount);
        transactionToComplete.setSellerAccount(sellerAccount);
        transactionToComplete.setTransactionCost(calculateTransactionCost(orderToProcess));
        transactionToComplete.setDateTimeTransaction(LocalDateTime.now());
        return transactionToComplete;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return rootRepository.save(transaction);
    }

}