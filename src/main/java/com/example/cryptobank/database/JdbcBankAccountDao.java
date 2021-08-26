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

    // TODO staat nog dubbel, hier en in JdbcCustomerDao
    /*private PreparedStatement insertBankAccount(BankAccount bankAccount, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into bankaccount (iban, balance) values (?,?);");
        ps.setString(1, bankAccount.getIban());
        ps.setDouble(2, bankAccount.getBalance());
        return ps;
    }*/



    // TODO checken of saveBankAccount ergens dubbel staat
   /* @Override
    public BankAccount save(BankAccount bankAccount) {
        jdbcTemplate.update(connection -> insertBankAccountStatement(bankAccount, connection));
        return bankAccount;
    }*/

    @Override
    public double getBalanceByIban(String iban) {
        String sql = "SELECT balance FROM bankaccount WHERE IBAN = ?";
        // Query for single record, according to this example needs: (sql, String.class, new Object[] { studentId })
        double balanceToRetieve = jdbcTemplate.queryForObject(sql, double.class, new Object[] {iban});
        return balanceToRetieve;
    }

    @Override
    public boolean checkBankAccount(String iban, double amount) {
        return false;
    }

    @Override
    public BankAccount withdraw(String iban, double amount, String description) {
        return null;
    }

    @Override
    public BankAccount deposit(String iban, double amount, String description) {
        return null;
    }

}