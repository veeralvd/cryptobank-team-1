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
    private final String INSERT_QUERY = "INSERT INTO bankaccount (iban, balance) VALUES (?,?)";
    private final String UPDATE_QUERY = "UPDATE bankaccount SET balance = ? WHERE iban = ?";
    private final String DELETE_QUERY = "DELETE FROM bankaccount WHERE iban = ?";

    private final Logger logger = LoggerFactory.getLogger(JdbcBankAccountDao.class);

    @Autowired
    public JdbcBankAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcBankAccountDao");
    }

    // TODO staat nog dubbel, hier en in JdbcCustomerDao
    /*private PreparedStatement insertBankAccount(BankAccount bankAccount, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
        ps.setString(1, bankAccount.getIban());
        ps.setDouble(2, bankAccount.getBalance());
        return ps;
    }*/

    private PreparedStatement updateBankAccount(String iban, double updatedBalance, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setDouble(1, updatedBalance);
        ps.setString(2, iban);
        return ps;
    }

    private PreparedStatement deleteBankAccount(String iban, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DELETE_QUERY);
        ps.setString(1, iban);
        return ps;
    }

    // TODO checken of saveBankAccount als aparte methode nodig is.

    //  Nieuw BankAccount wordt eenmalig opgeslagen bij registratie Customer:
    //  JdbcCustomerDao: public Customer save(Customer customer) {
    //        jdbcTemplate.update(connection -> insertBankAccount(customer.getBankAccount(), connection));
   /* @Override
    public BankAccount save(BankAccount bankAccount) {
        jdbcTemplate.update(connection -> insertBankAccountStatement(bankAccount, connection));
        return bankAccount;
    }*/

    @Override
    public double getBalanceByIban(String iban) {
        String sql = "SELECT balance FROM bankaccount WHERE iban = ?";
        // Query for single record, according to this example needs: (sql, String.class, new Object[] { studentId })
        double balanceRetrieved = jdbcTemplate.queryForObject(sql, double.class, new Object[] {iban});
        return balanceRetrieved;
    }

    @Override
    public double deposit(String iban, double amount) {
        double updatedBalance = this.getBalanceByIban(iban) - amount;
        jdbcTemplate.update(connection -> updateBankAccount(iban, updatedBalance, connection));
        return updatedBalance;
    }

    @Override
    public double withdraw(String iban, double amount) {
        double updatedBalance = getBalanceByIban(iban) + amount;
        jdbcTemplate.update(connection -> updateBankAccount(iban, updatedBalance, connection));
        return updatedBalance;
    }

    @Override
    public boolean checkBankAccount(String iban, double amount) {
        return false;
    }

    /*@Override
    public void deleteBankAccount(String iban) {
        jdbcTemplate.update(connection -> deleteBankAccount(iban, connection));
    }*/



}