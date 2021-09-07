package com.example.cryptobank.database;

import com.example.cryptobank.domain.*;
import com.example.cryptobank.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcPortfolioDao implements PortfolioDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcPortfolioDao.class);

    private JdbcTemplate jdbcTemplate;
    private final String UPDATE_QUERY = "UPDATE ownedasset SET aantalEenheden = ? WHERE iban=? AND abbreviation=?";

    @Autowired
    public JdbcPortfolioDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcPortfolioDao");
    }

    // gebruik indien asset in kwestie nog niet eerder in de portfolio
    private PreparedStatement insertAssetInPortfolioStatement (Order order, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO ownedasset_table (IBAN, abbreviation, aantalEenheden) values (?, ?, ?)"
        );
        ps.setString(1, order.getBankAccount().getIban());
        ps.setString(2, order.getAsset().getAbbreviation());
        ps.setDouble(3, order.getAssetAmount());
        return ps;
    }

/*    private PreparedStatement updatePortfolioStatementPositive (Customer customer, Transaction transaction,
                                                                Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        // double storedAssetAmount = customer.getPortfolio().getAssetMap().get(transaction.getAsset());
        ps.setDouble(1, transaction.getAssetAmount());
        ps.setString(2, transaction.getBuyerAccount().getIban());
        ps.setString(3, transaction.getAsset().getAbbreviation());
        return ps;
    }*/

    private PreparedStatement updatePortfolioStatementPositive (double updatedAmount, Transaction transaction,
                                                                Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setDouble(1, updatedAmount);
        ps.setString(2, transaction.getBuyerAccount().getIban());
        ps.setString(3, transaction.getAsset().getAbbreviation());
        return ps;
    }

    @Override
    public double updateAssetAmountPositive(double transactionAssetAmount, Customer customer, Transaction transaction) {

        double currentAmount = customer.getPortfolio().getAssetMap().get(transaction.getAsset());
        double updatedAmount = currentAmount + transactionAssetAmount;
        int status = jdbcTemplate.update(connection -> updatePortfolioStatementPositive(updatedAmount, transaction, connection));
        if (status == 1) {
            return updatedAmount;
        } else {
            return currentAmount;
        }
    }

    // Bij verkoop van een deel van opgeslagen asset wordt de hoeveelheid verminderd
    private PreparedStatement updatePortfolioStatementNegative (double updatedAmount, Transaction transaction,
                                                                Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setDouble(1, updatedAmount);
        ps.setString(2, transaction.getSellerAccount().getIban());
        ps.setString(3, transaction.getAsset().getAbbreviation());
        return ps;
    }

    @Override
    public double updateAssetAmountNegative(double transactionAssetAmount, Customer customer, Transaction transaction) {

        double currentAmount = customer.getPortfolio().getAssetMap().get(transaction.getAsset());
        double updatedAmount = currentAmount - transactionAssetAmount;
        int status = jdbcTemplate.update(connection -> updatePortfolioStatementPositive(updatedAmount, transaction, connection));
        if (status == 1) {
            return updatedAmount;
        } else {
            return currentAmount;
        }
    }

    private PreparedStatement deletePortfolioStatement (Order order, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM ownedasset_table WHERE iban = ?"
        );
        ps.setString(1, order.getBankAccount().getIban());
        return ps;
    }

    @Override
    public Map<String, Double> getAssetmapByIban (String iban){
        String sql = "SELECT abbreviation, aantalEenheden FROM ownedasset WHERE iban = ?";
        Map<String, Double> results = new HashMap<>();
        jdbcTemplate.query(sql, (ResultSet rs) -> {
            results.put(rs.getString("abbreviation"),
                        rs.getDouble("aantalEenheden"));
        }, iban);
        return results;
    }


}
