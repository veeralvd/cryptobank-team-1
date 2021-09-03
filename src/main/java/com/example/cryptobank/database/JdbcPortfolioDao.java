package com.example.cryptobank.database;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcPortfolioDao implements PortfolioDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcPortfolioDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPortfolioDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcPortfolioDao");
    }

    // gebruik indien asset in kwestie nog niet eerder in de portfolio
    private PreparedStatement insertAssetInPortfolioStatement (Purchase purchase, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO ownedasset_table (IBAN, abbreviation, aantalEenheden) values (?, ?, ?)"
        );
        ps.setString(1, purchase.getCustomer().getBankAccount().getIban());
        ps.setString(2, purchase.getAsset().getAbbreviation());
        ps.setDouble(3, purchase.getAmount());
        return ps;
    }

    private PreparedStatement updatePortfolioStatementPositive (Portfolio portfolio, Customer customer,
                                                                Purchase purchase, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE ownedasset_table SET aantalEenheden = ? WHERE iban=? AND abbreviation=?"
        );
        // TODO: 25-8-2021 aantal in bezit ophalen via customer.portfolio en dan uit de map halen
        double storedAssetAmount = portfolio.getAssetMap().get(purchase.getAsset());
        ps.setDouble(1, purchase.getAmount() + storedAssetAmount);
        ps.setString(2, purchase.getCustomer().getBankAccount().getIban());
        ps.setString(3, purchase.getAsset().getAbbreviation());
        return ps;
    }

    // Bij verkoop van een deel van opgeslagen asset wordt de hoeveelheid verminderd
    private PreparedStatement updatePortfolioStatementNegative (Portfolio portfolio, Customer customer,
                                                                Purchase purchase, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE ownedasset_table SET aantalEenheden = ? WHERE iban=? AND abbreviation=?"
        );
        // TODO: 25-8-2021 aantal in bezit ophalen via customer.portfolio en dan uit de map halen
        double storedAssetAmount = portfolio.getAssetMap().get(purchase.getAsset());
        ps.setDouble(1, purchase.getAmount() - storedAssetAmount);
        ps.setString(2, purchase.getCustomer().getBankAccount().getIban());
        ps.setString(3, purchase.getAsset().getAbbreviation());
        return ps;
    }

    private PreparedStatement deletePortfolioStatement (Purchase purchase, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM ownedasset_table WHERE iban = ?"
        );
        ps.setString(1, purchase.getCustomer().getBankAccount().getIban());
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
