package com.knight.cryptobank.database;

import com.knight.cryptobank.domain.Order;
import com.knight.cryptobank.domain.Portfolio;
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

    private PreparedStatement updatePortfolioStatementPositive (Portfolio portfolio,
                                                                Order order, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE ownedasset_table SET aantalEenheden = ? WHERE iban=? AND abbreviation=?"
        );
        // TODO: 25-8-2021 aantal in bezit ophalen via customer.portfolio en dan uit de map halen
        double storedAssetAmount = portfolio.getAssetMap().get(order.getAsset());
        ps.setDouble(1, order.getAssetAmount() + storedAssetAmount);
        ps.setString(2, order.getBankAccount().getIban());
        ps.setString(3, order.getAsset().getAbbreviation());
        return ps;
    }

    // Bij verkoop van een deel van opgeslagen asset wordt de hoeveelheid verminderd
    private PreparedStatement updatePortfolioStatementNegative (Portfolio portfolio,
                                                                Order order, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE ownedasset_table SET aantalEenheden = ? WHERE iban=? AND abbreviation=?"
        );
        // TODO: 25-8-2021 aantal in bezit ophalen via customer.portfolio en dan uit de map halen
        double storedAssetAmount = portfolio.getAssetMap().get(order.getAsset());
        ps.setDouble(1, order.getAssetAmount() - storedAssetAmount);
        ps.setString(2, order.getBankAccount().getIban());
        ps.setString(3, order.getAsset().getAbbreviation());
        return ps;
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
