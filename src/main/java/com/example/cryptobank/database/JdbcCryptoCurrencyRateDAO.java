package com.example.cryptobank.database;

import com.example.cryptobank.domain.CryptoCurrencyRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcCryptoCurrencyRateDAO implements CryptoCurrencyRateDAO{

    private final Logger logger = LoggerFactory.getLogger(JdbcCryptoCurrencyRateDAO.class);
    private JdbcTemplate jdbcTemplate;

    public JdbcCryptoCurrencyRateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New CryptoCurrencyRateDAO");
    }


    private PreparedStatement insertCryptoCurrencyStatement(CryptoCurrencyRate cryptoCurrencyRate, Connection connection)
        throws SQLException {
        String sql = "insert into crypto_currency_rate (abbreviation, datetime, value) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cryptoCurrencyRate.getAbbreviation());
        preparedStatement.setString(2, String.valueOf(cryptoCurrencyRate.getDateTime()));
        preparedStatement.setDouble(3, cryptoCurrencyRate.getCryptoRate());
        return preparedStatement;
    }



    @Override
    public CryptoCurrencyRate save(CryptoCurrencyRate cryptoCurrencyRate) {
        logger.info("cryptoCurrencySaver aangeroepn");
        jdbcTemplate.update(connection -> insertCryptoCurrencyStatement(cryptoCurrencyRate, connection));
        return cryptoCurrencyRate;
    }


    private static class CryptoCurrencyRateRowMapper implements RowMapper<CryptoCurrencyRate> {

        @Override
        public CryptoCurrencyRate mapRow(ResultSet resultSet, int i) throws SQLException {
            String abbreviation = resultSet.getString("abbreviation");
            String date = resultSet.getString("datetime");
            double cryptoRate = resultSet.getDouble("value");
            return new CryptoCurrencyRate(abbreviation, cryptoRate, date); //returns date as string, gets handled in domain
        }
    }

    @Override
    public ArrayList<CryptoCurrencyRate> getAll() {
        String sql = "SELECT * FROM crypto_currency_rate";
        List<CryptoCurrencyRate> allCurrencies = jdbcTemplate.query(sql, new CryptoCurrencyRateRowMapper());
        return (ArrayList<CryptoCurrencyRate>) allCurrencies;
    }

    @Override
    public ArrayList<CryptoCurrencyRate> findByAbbreviation(String abbreviation) {
        List<CryptoCurrencyRate> allCurrencies = jdbcTemplate.query(
                "select * from crypto_currency_rate where abbreviation = ?",
                new CryptoCurrencyRateRowMapper(), abbreviation);
        return (ArrayList<CryptoCurrencyRate>) allCurrencies;
    }

}