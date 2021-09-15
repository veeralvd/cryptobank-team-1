package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.*;
import com.example.cryptobank.dto.OrderDto;
import com.example.cryptobank.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private RootRepository rootRepository;
    private SendMailService mailService;
    private Bank bank = Bank.getInstance();
    private BankAccount buyerAccount;
    private BankAccount sellerAccount;
    private String assetAbbr;
    private String ibanBuyer;
    private String ibanSeller;
    private final double TRANSACTION_RATE = 0.03;   // TODO transaction rate instelbaar maken

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository, SendMailService mailService) {
        this.mailService = mailService;
        this.rootRepository = rootRepository;
        logger.info("New TransactionService");
    }

    public TransactionDto getTransactionDto(Transaction transaction) {
        int transactionId = transaction.getTransactionId();
        String ibanBuyer = transaction.getBuyerAccount().getIban();
        String ibanSeller = transaction.getSellerAccount().getIban();
        String assetAbbr = transaction.getAsset().getAbbreviation();
        double assetAmount = transaction.getAssetAmount();
        double singleAssetPrice = transaction.getAssetPrice();
        double transactionCost = transaction.getTransactionCost();
        LocalDateTime dateTimeProcessed = transaction.getDateTimeTransaction();
        return new TransactionDto(transactionId, ibanBuyer, ibanSeller, assetAbbr, assetAmount,
                singleAssetPrice, transactionCost, dateTimeProcessed);
    }

    public TransactionDto findByTransactionId(int transactionId) {
        Transaction transaction = rootRepository.findByTransactionId(transactionId);
        return getTransactionDto(transaction);
    }

    public TransactionDto completeTransaction(OrderDto orderToProcess) {

        // koop van bank:
        ibanBuyer = orderToProcess.getIban();
        buyerAccount = rootRepository.getBankAccountByIban(ibanBuyer);
        sellerAccount = bank.getBankAccount();

  /*      // verkoop aan bank
        buyerAccount = bank.getBankAccount();
        sellerAccount = orderToProcess.getBankAccount();*/

        if (validateCreditLimitBuyer(orderToProcess)
                && validatePortfolioSellerContainsAsset(orderToProcess)
                && validateAssetAmountSeller(orderToProcess)) {

            updateBankAccount(orderToProcess);
            updatePortfolio(orderToProcess);
            Transaction transactionToComplete = assembleNewTransaction(orderToProcess);
            saveTransaction(getTransactionDto(transactionToComplete));
            logger.info("Transactie opgeslagen");
            sentConfirmationMailTransaction(getTransactionDto(transactionToComplete));
            return getTransactionDto(transactionToComplete);
        }

        logger.info("Transactie NIET opgeslagen");
        return null;
    }

    private double calculateAssetCost(OrderDto orderToProcess) {
        Asset asset = rootRepository.getByAbbreviation(orderToProcess.getAssetAbbr());
        double currentSingleAssetPrice = asset.getRate().getCryptoRate();
        double assetCost = currentSingleAssetPrice * orderToProcess.getAssetAmount();
        return assetCost;
    }

    private double calculateTransactionCost(OrderDto orderToProcess) {
        double transactionCost = calculateAssetCost(orderToProcess) * TRANSACTION_RATE;
        return transactionCost;
    }

    // hier nog voor alleen koop van bank: bedrag te betalen door klant = bedrag te ontvangen door bank
    private double calculateAmountToPayReceive(OrderDto orderToProcess) {
        double assetCost = calculateAssetCost(orderToProcess);
        double transactionCost = calculateTransactionCost(orderToProcess);
        return assetCost + transactionCost;
    }

    private boolean validateCreditLimitBuyer(OrderDto orderToProcess) {
        logger.info("Check if buyer has enough money");
        double amountToPay = calculateAmountToPayReceive(orderToProcess);
        if (amountToPay >= buyerAccount.getBalance()) {
            logger.info("Insufficient funds !!!");
            return false;
        }
        return true;
    }

    private boolean validatePortfolioSellerContainsAsset(OrderDto orderToProcess) {
        assetAbbr = orderToProcess.getAssetAbbr();
        ibanSeller = sellerAccount.getIban();
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
    private boolean checkIfPortfolioBuyerContainsAsset(OrderDto orderToProcess) {
        assetAbbr = orderToProcess.getAssetAbbr();
        ibanBuyer = buyerAccount.getIban();
        List<String> assetAbbrList = rootRepository.getAbbreviationsByIban(ibanBuyer);
        for (String abbreviation: assetAbbrList) {
            if (abbreviation.equals(assetAbbr)) {
                return true;
            }
        }
        logger.info("Portfolio buyer does not contain asset");
        return false;
    }

    private boolean validateAssetAmountSeller(OrderDto orderToProcess) {
        logger.info("Check if seller has sufficient assets");
        double amountToRemove = orderToProcess.getAssetAmount();
        assetAbbr = orderToProcess.getAssetAbbr(); // dubbele code
        ibanSeller = sellerAccount.getIban(); // dubbele code
        double amountInPortfolio = rootRepository.getAssetAmountByIbanAndAbbr(ibanSeller, assetAbbr);
        if (amountInPortfolio >= amountToRemove) {
            return true;
        }
        logger.info("Not enough Assets to sell !!!");
        return false;
    }

    //Hier nog alleen voor aankoop van bank
    private void updateBankAccount(OrderDto orderToProcess) {
        double amountToPayReceive = calculateAmountToPayReceive(orderToProcess);
        double updatedBalanceBuyer = rootRepository.withdraw(buyerAccount.getIban(), amountToPayReceive);
        double updatedBalanceSeller = rootRepository.deposit(sellerAccount.getIban(), amountToPayReceive);
        logger.info("BalanceBuyer update: " + updatedBalanceBuyer + ", BalanceSeller updated: " + updatedBalanceSeller
                + ", with: " + amountToPayReceive);
    }

    private void updatePortfolio(OrderDto orderToProcess) {
        Transaction transactionToComplete = assembleNewTransaction(orderToProcess);
        String assetAbbr = transactionToComplete.getAsset().getAbbreviation();
        double assetAmount = transactionToComplete.getAssetAmount();
        boolean portfolioBuyerContainsAsset = checkIfPortfolioBuyerContainsAsset(orderToProcess);
        if (!portfolioBuyerContainsAsset) {
            rootRepository.insertAssetIntoPortfolio(transactionToComplete);
            logger.info("New asset inserted into ownedAsset table: " + assetAbbr);
        }
        rootRepository.updateAssetAmountNegative(transactionToComplete);
        rootRepository.updateAssetAmountPositive(transactionToComplete);
        logger.info("Asset removed from portfolio seller & added to portfolio buyer: " + assetAbbr + ", " + assetAmount);
    }

    private Transaction assembleNewTransaction(OrderDto orderToProcess) {
        Asset asset = rootRepository.getByAbbreviation(orderToProcess.getAssetAbbr());
        Transaction transactionToComplete = new Transaction();
        transactionToComplete.setAsset(asset);
        transactionToComplete.setAssetAmount(orderToProcess.getAssetAmount());
        transactionToComplete.setAssetPrice(asset.getRate().getCryptoRate());
        transactionToComplete.setBuyerAccount(buyerAccount);
        transactionToComplete.setSellerAccount(sellerAccount);
        transactionToComplete.setTransactionCost(calculateTransactionCost(orderToProcess));
        transactionToComplete.setDateTimeTransaction(LocalDateTime.now());
        return transactionToComplete;
    }

    public TransactionDto saveTransaction(TransactionDto transaction) {
        return rootRepository.saveTransaction(transaction);
    }

    public void sentConfirmationMailTransaction(TransactionDto transaction) {
        Mail mail = new Mail();
        mail.setRecipient("anne.van.der.veer@hva.nl");
        mail.setSubject("Confirmation transaction Cryptoknights");
        mail.setMessage(transaction.toString());
        mailService.sendMail(mail);
    }

}