package com.example.cryptobank.database;

import com.example.cryptobank.domain.Asset;
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
import java.util.List;


/**
 * @author Sarah-Jayne Nogarede
 * Dit is de JDBC DAO voor het object 'Purchase'. Deze DAO implementeert de methoden save, findByTransactionNumber
 * en getAllByCustomer uit de PurchaseDao Interface.
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
        PreparedStatement preparedStatement = connection.prepareStatement("insert into purchase (cusomerUsername, dateTime, " +
                "assetAbbr, amount, transactionNumber) values (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, transaction.getCustomer().getUsername());
        preparedStatement.setString(2, String.valueOf(transaction.getLocalDateTime()));
        preparedStatement.setString(3, transaction.getAsset().getAbbreviation());
        preparedStatement.setDouble(4, transaction.getAmount());
        preparedStatement.setInt(5, transaction.getTransactionNumber());
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
            LocalDateTime dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime();
            double amount = resultSet.getDouble("amount");
            int transactionNumber = resultSet.getInt("transactionNumber");
            Customer customer = null;
            Asset asset = null;
            Transaction transaction = new Transaction(customer, dateTime, asset, amount, transactionNumber);
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

} // end of class JdbcTransactionDao
