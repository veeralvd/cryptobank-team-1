package com.example.cryptobank.database;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.CryptoCurrencyRate;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Purchase;
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


/**
 * @author Sarah-Jayne Nogarede
 * Dit is de JDBC DAO voor het object 'Purchase'. Deze DAO implementeert de methoden save, findByTransactionNumber
 * en getAllByCustomer uit de PurchaseDao Interface.
 */
@Repository
public class JdbcPurchaseDao implements PurchaseDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcPurchaseDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPurchaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcPurchaseDao");
    }

    private PreparedStatement insertPurchaseStatement(Purchase purchase, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into purchase (cusomerUsername, dateTime, " +
                "assetAbbr, amount, transactionNumber) values (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, purchase.getCustomer().getUsername());
        preparedStatement.setString(2, String.valueOf(purchase.getLocalDateTime()));
        preparedStatement.setString(3, purchase.getAsset().getAbbreviation());
        preparedStatement.setDouble(4, purchase.getAmount());
        preparedStatement.setInt(5, purchase.getTransactionNumber());
        return preparedStatement;
    }

    @Override
    public Purchase save(Purchase purchase) {
        logger.info("purchaseDao.save aangeroepen");
        jdbcTemplate.update(connection -> insertPurchaseStatement(purchase, connection));
        return purchase;
    }


    private static class PurchaseRowMapper implements RowMapper<Purchase> {

        private JdbcCustomerDAO jdbcCustomerDAO;

        @Override
        public Purchase mapRow(ResultSet resultSet, int i) throws SQLException {
            String customerUsername = resultSet.getString("customerUsername");
            LocalDateTime dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime();
            String assetAbbr = resultSet.getString("assetAbbr");
            double amount = resultSet.getDouble("amount");
            int transactionNumber = resultSet.getInt("transactionNumber");
            Customer customer = jdbcCustomerDAO.findByUsername(customerUsername);
            Purchase purchase = new Purchase(customer, dateTime, assetAbbr, amount, transactionNumber);
            return purchase;
        }

    } // end of nested class AssetRowMapper

} // end of class JdbcPurchaseDao
