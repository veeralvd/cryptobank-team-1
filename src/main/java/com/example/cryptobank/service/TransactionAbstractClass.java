package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Bank;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Order;
import com.example.cryptobank.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public abstract class TransactionAbstractClass {

    private RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(TransactionAbstractClass.class);

    private BankAccount buyerAccount;
    private BankAccount sellerAccount;
    private static final BankAccount BANK_ACCOUNT = Bank.getInstance().getBankAccount();
    private final double TRANSACTION_RATE = 0.03;

    public TransactionAbstractClass(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New TransactionAbstractClass");
    }

    final void completeTransaction(Order orderToProcess) {



       /* isBuyingOrder(orderToProcess);
        if (isBuyingOrder(orderToProcess)) {
            buyerAccount = orderToProcess.getBankAccount();
            sellerAccount = BANK_ACCOUNT;
        } else {
            buyerAccount = BANK_ACCOUNT;
            sellerAccount = orderToProcess.getBankAccount();
        }*/

        calculateAssetCost(orderToProcess);
        calculateTransactionCost(orderToProcess);
        calculateTotalCost(orderToProcess);

//        validateCreditLimitBuyer(orderToProcess);
//        validatePortfolioContainsAsset(orderToProcess);
//        validateAssetAmountSeller(orderToProcess);

        // && validateAssetAmountSeller(orderToProcess)

        if (validateCreditLimitBuyer(orderToProcess) && validatePortfolioContainsAsset(orderToProcess) ) {
            updateBankAccount();
            updatePortfolio();
            assembleNewTransaction(orderToProcess);
            saveTransaction(orderToProcess);
        }
    }

   /* boolean isBuyingOrder(Order orderToProcess) {
        logger.info("Check of het om een kooporder gaat (alleen aankoop van bank voor nu)");
        if (orderToProcess.isBuyingOrder()) {
            return true;
        }
        return false;
    };*/

    double calculateAssetCost(Order orderToProcess) {
        double assetCost = orderToProcess.getDesiredPrice() * orderToProcess.getAssetAmount();
        return assetCost;
    }

    // default gedeeld door twee
    double calculateTransactionCost(Order orderToProcess) {
        double transactionCost = (calculateAssetCost(orderToProcess) * TRANSACTION_RATE) / 2;
        return transactionCost;
    }

    double calculateTotalCost(Order orderToProcess) {
        String ibanBuyer = buyerAccount.getIban();
        String ibanSeller = sellerAccount.getIban();
        String ibanBank = BANK_ACCOUNT.getIban();
        double transactioncostBuyer = 0.0;
        double transactioncostSeller = 0.0;
        //double transactionCostBuyer = buyerAccount==bank?0.0:transactionCost;
        //double  transactionCostSeller = sellerAccount == bank?0.0:transactionCost;

        if (ibanBuyer.equals(ibanBank)) {
            transactioncostBuyer = 0.0;
        } else if (ibanSeller.equals(ibanBank)) {
            transactioncostSeller = 0.0;
        }

        return transactioncostBuyer;
    }







    boolean validateCreditLimitBuyer(Order orderToProcess) {
        logger.info("Check if buyer has enough money");
        double amountToPay = calculateTotalCost(orderToProcess);
        if (amountToPay >= buyerAccount.getBalance()) {
            return false;
        }
        return true;
    }

    boolean validatePortfolioContainsAsset(Order orderToProcess) {
        String assetAbbr = orderToProcess.getAsset().getAbbreviation();
        String ibanSeller = sellerAccount.getIban();
        List<String> assetAbbrList = rootRepository.getAbbreviationsByIban(ibanSeller);
        for (String abbreviation: assetAbbrList) {
            if (abbreviation.equals(assetAbbr)) {
                return true;
            }
        }
        return false;
    }

  /*  boolean validateAssetAmountSeller(Order orderToProcess) {
        logger.info("Check if seller has sufficient assets");
        double amountToRemove = orderToProcess.getAssetAmount();
        String assetAbbr = orderToProcess.getAsset().getAbbreviation(); // dubbele code
        String ibanSeller = sellerAccount.getIban(); // dubbele code
        double amountInPortfolio = rootRepository.getAssetAmountByIbanAndAbbr(ibanSeller, assetAbbr);
        if (amountInPortfolio >= amountToRemove) {
            return true;
        }
        return false;
    }*/

    void updateBankAccount() {
        logger.info("Withdraw money from bankaccount buyer / deposit money to bankaccount seller");
    }

    void updatePortfolio() {
        logger.info("Remove assets from portfolio seller / add assets to portfolio buyer");
    }

    Transaction assembleNewTransaction(Order orderToProcess) {
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

    void saveTransaction(Order orderToProcess) {
        logger.info("Save transation in transaction table");
        Transaction transactionToSave = assembleNewTransaction(orderToProcess);
        rootRepository.save(transactionToSave);
    }

}