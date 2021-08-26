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
public class JdbcPortfolioDao {
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

    /*public Map<Asset, Double> test (String iban){

        return jdbcTemplate.query("SELECT * FROM ownedasset_table WHERE iban = ?;"
                , new PreparedStatementSetter[] {iban}, (ResultSet rs) -> {
                    HashMap<Asset, Double> results = new HashMap<>();
                    while(rs.next()){
                        Asset asset = AssetDao.getAssetByAbbr (rs.getString("abbreviation"));
                        results.put(asset), rs.getDouble("aantalEenheden");
                    }
                    return results;
                }).get(0);
    }*/



    /*public Portfolio findByIban (String iban){

        //String sql = "SELECT * FROM ownedasset_table WHERE iban = ? ;";

        HashMap<Asset, Double> results = jdbcTemplate.query("SELECT * FROM ownedasset_table WHERE iban = ? ;"
                , new Object[] {iban}, (ResultSet rs) -> {
                    results = new HashMap<>();
                    while(rs.next()){
                        Asset asset = AssetDao.getAssetByAbbr (rs.getString("abbreviation"));
                        results.put(asset), rs.getDouble("aantalEenheden"));
                    }

                    return null;
                }).get(0);
    }


    public Map<Integer,Integer> getAvailableTime(Date date, Integer guru_fid) {
        return jdbctemp.query("Select a.hour, s.duration from appointment as a inner join services as s on a.service_fid=s.id where date=? and guru_fid=? "
                ,new Object[] { date, guru_fid }, (ResultSet rs) -> {HashMap<Integer,Integer> results = new HashMap<>();
                    while (rs.next()) {
                        results.put(rs.getInt("a.hour"), rs.getInt("s.duration"));
                    }
                    return results;
                }).get(0);

    }*/
}
