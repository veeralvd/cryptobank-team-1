package com.knight.cryptobank.service;

import com.knight.cryptobank.database.BankAccountDao;
import com.knight.cryptobank.database.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    private BankAccountDao bankAccountDao;
    private RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    public BankAccountService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New BankAccountService");
    }

    public double getBalanceByIban(String iban) {
        double balanceToRetrieve = rootRepository.getBalanceByIban(iban);
        return balanceToRetrieve;
    }

    public double deposit(String iban, double amount) {
        double updatedBalance = rootRepository.deposit(iban, amount);
        return updatedBalance;
    }

    public double withdraw(String iban, double amount) {
        double updatedBalance = rootRepository.withdraw(iban, amount);
        return  updatedBalance;
    }

}