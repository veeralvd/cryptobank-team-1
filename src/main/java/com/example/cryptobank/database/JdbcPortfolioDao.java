package com.example.cryptobank.database;

import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class JdbcPortfolioDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcPortfolioDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPortfolioDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcPortfolioDao");
    }

    private PreparedStatement insertPortfolioStatement (Portfolio portfolio, Customer customer,
                                                        Purchase purchase, Connection connection) throws SQLException {
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

    private PreparedStatement deletePortfolioStatement (Portfolio portfolio, Customer customer,
                                                        Purchase purchase, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM ownedasset_table WHERE iban = ?"
        );
        ps.setString(1, purchase.getCustomer().getBankAccount().getIban());
        return ps;
    }

}