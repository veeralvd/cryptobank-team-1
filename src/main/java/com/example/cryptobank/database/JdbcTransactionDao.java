package com.example.cryptobank.database;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Sarah-Jayne Nogarede
 * Dit is de JDBC DAO voor het object 'Transaction'. Deze DAO implementeert de methoden save, findByTransactionNumber
 * en getAllByIban uit de TransactionDao Interface.
 */
@Repository
public class JdbcTransactionDao implements TransactionDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcTransactionDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcTransactionDao");
    }

    private PreparedStatement insertTransactionStatement(Transaction transaction, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into transaction (transactionNumber, buyerAccount, " +
                "sellerAccount, dateTime, assetAbbr, amount, sellingPrice) values (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, transaction.getTransactionNumber());
        preparedStatement.setString(2, transaction.getBuyerAccount().getIban());
        preparedStatement.setString(3, transaction.getSellerAccount().getIban());
        preparedStatement.setString(2, String.valueOf(transaction.getLocalDateTime()));
        preparedStatement.setString(3, transaction.getAsset().getAbbreviation());
        preparedStatement.setDouble(4, transaction.getAmount());
        preparedStatement.setDouble(5, transaction.getSellingPrice());
        return preparedStatement;
    }

    @Override
    public Transaction save(Transaction transaction) {
        logger.info("transactionDao.save aangeroepen");
        jdbcTemplate.update(connection -> insertTransactionStatement(transaction, connection));
        return transaction;
    }


    private static class TransactionRowMapper implements RowMapper<Transaction> {

        @Override
        public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
            int transactionNumber = resultSet.getInt("transactionNumber");
            BankAccount buyerAccount = null;
            BankAccount sellerAccount = null;
            LocalDateTime dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime();
            Asset asset = null;
            double amount = resultSet.getDouble("amount");
            double sellingPrice = resultSet.getDouble("sellingPrice");
            Transaction transaction = new Transaction(transactionNumber, buyerAccount, sellerAccount, dateTime, asset, amount, sellingPrice);
            return transaction;
        }

    } // end of nested class AssetRowMapper

    @Override
    public Transaction findByTransactionNumber(int transactionNumber) {
        String sql = "SELECT * from transaction where transactionNumber = ?";
        List<Transaction> transactionToFind = jdbcTemplate.query(sql, new JdbcTransactionDao.TransactionRowMapper(), transactionNumber);
        if (transactionToFind.size() == 1) {
            return transactionToFind.get(0);
        }
        return null;
    }

    //TODO nu 'vind alle transacties met deze iban', niet gesplitst in koper of verkoper. Goed of splitsen?
    @Override
    public ArrayList<Transaction> getAllByIban (String iban) {
        String sql = "SELECT * from transaction where ibanBuyer = ? or ibanSeller = ?";
        List<Transaction> allTransactions = jdbcTemplate.query(sql, new JdbcTransactionDao.TransactionRowMapper());
        return (ArrayList<Transaction>) allTransactions;
    }

} // end of class JdbcTransactionDao
