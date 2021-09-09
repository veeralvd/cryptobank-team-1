package com.example.cryptobank.database;

import com.example.cryptobank.domain.*;
import com.example.cryptobank.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
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
    private PreparedStatement insertAssetInPortfolioStatement (Transaction transaction, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO ownedasset (IBAN, abbreviation, aantalEenheden) values (?, ?, ?)"
        );
        ps.setString(1, transaction.getBuyerAccount().getIban());
        ps.setString(2, transaction.getAsset().getAbbreviation());
        ps.setDouble(3, transaction.getAssetAmount());
        return ps;
    }

    @Override
    public void insertAssetIntoPortfolio(Transaction transaction){
        jdbcTemplate.update(connection -> insertAssetInPortfolioStatement(transaction, connection));
    }

    private PreparedStatement updatePortfolioStatementPositive (double updatedAmount, Transaction transaction,
                                                                Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setDouble(1, updatedAmount);
        ps.setString(2, transaction.getBuyerAccount().getIban());
        ps.setString(3, transaction.getAsset().getAbbreviation());
        return ps;
    }

    @Override
    public double updateAssetAmountPositive(Transaction transaction) {
        String iban = transaction.getBuyerAccount().getIban();
        String abbr = transaction.getAsset().getAbbreviation();
        double transactionAssetAmount = transaction.getAssetAmount();
        double currentAmount = getAssetAmountByIbanAndAbbr(iban, abbr);
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
    public double updateAssetAmountNegative(Transaction transaction) {
        String iban = transaction.getSellerAccount().getIban();
        String abbr = transaction.getAsset().getAbbreviation();
        double transactionAssetAmount = transaction.getAssetAmount();
        double currentAmount = getAssetAmountByIbanAndAbbr(iban, abbr);
//        double currentAmount = customer.getPortfolio().getAssetMap().get(transaction.getAsset());
        double updatedAmount = currentAmount - transactionAssetAmount;
        int status = jdbcTemplate.update(connection -> updatePortfolioStatementNegative(updatedAmount, transaction, connection));
        if (status == 1) {
            return updatedAmount;
        } else {
            return currentAmount;
        }
    }

    private PreparedStatement deletePortfolioStatement (Transaction transaction, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM ownedasset_table WHERE iban = ?"
        );
        ps.setString(1, transaction.getSellerAccount().getIban());
        return ps;
    }

    @Override
    public void deleteAssetFromPortfolio(Transaction transaction){
        jdbcTemplate.update(connection -> deletePortfolioStatement(transaction, connection));
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

    public double getAssetAmountByIbanAndAbbr (String iban, String abbr){
        String sql = "SELECT aantalEenheden FROM ownedasset WHERE IBAN = ? AND abbreviation = ?";
        try {
            return jdbcTemplate.queryForObject(sql, double.class, iban, abbr);
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Empty result");
            // TODO Empty result afvangen
            return -1.0;
        }
    }

    public List<String> getAbbreviationsByIban(String iban){
        String sql = "SELECT abbreviation FROM ownedasset WHERE iban = ?";
        List<String> abbrList = jdbcTemplate.queryForList(sql, String.class, iban);
        return abbrList;
    }


}
