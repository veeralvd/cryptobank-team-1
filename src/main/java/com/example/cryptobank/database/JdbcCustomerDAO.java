package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class JdbcCustomerDAO implements CustomerDAO{
    private final Logger logger = LoggerFactory.getLogger(JdbcCustomerDAO.class);
    private JdbcTemplate jdbcTemplate;

    public JdbcCustomerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcCustomerDAO");
    }

    @Override
    public Customer save(Admin admin) {
        return null;
    }

    private static class CustomerRowMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            LocalDate dateOfBirth = resultSet.getDate("dateofbirth").toLocalDate();
            String street = resultSet.getString("street");
            String zipcode = resultSet.getString("zipcode");
            int houseNumber = resultSet.getInt("housenumber");
            String addition = resultSet.getString("addition");
            return null;
        }
    }

    @Override
    public Customer findByUsername(String username) {
        return null;
    }
}