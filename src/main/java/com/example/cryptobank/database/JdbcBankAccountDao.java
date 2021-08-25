package com.example.cryptobank.database;

import com.example.cryptobank.domain.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class JdbcBankAccountDao implements BankAccountDao {

    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(JdbcBankAccountDao.class);

    @Autowired
    public JdbcBankAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcBankAccountDao");
    }

    private PreparedStatement insertBankAccountStatement(BankAccount bankAccount, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into bankaccount (iban, balance) values (?,?);");
        ps.setString(1, bankAccount.getIban());
        ps.setDouble(2, bankAccount.getBalance());
        return ps;
    }



    /*@Override
    public BankAccount save(BankAccount bankAccount) {
        jdbcTemplate.update(connection -> insertBankAccountStatement(bankAccount, connection));
        return bankAccount;
    }

    @Override
    public BankAccount findByIban(String iban) {
        return null;
    }

    @Override
    public boolean checkBankAccount(TestIban iban, double amount) {
        return false;
    }

    @Override
    public BankAccount withdraw(TestIban iban, double amount, String description) {
        return null;
    }

    @Override
    public BankAccount deposit(TestIban iban, double amount, String description) {
        return null;
    }*/

}