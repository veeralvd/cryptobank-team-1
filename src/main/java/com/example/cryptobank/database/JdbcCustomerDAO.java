package com.example.cryptobank.database;

import com.example.cryptobank.domain.Address;
import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcCustomerDAO implements CustomerDAO{
    private final Logger logger = LoggerFactory.getLogger(JdbcCustomerDAO.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCustomerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcCustomerDAO");
    }

    private PreparedStatement insertCustomer(Customer customer, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into customer (username, password, salt, firstname, lastname, dateofbirth, " +
                        "socialsecuritynumber, street, zipcode, housenumber, addition) values (?,?,?,?,?,?,?,?,?,?,?)"
        );
        preparedStatement.setString(1, customer.getUsername());
        preparedStatement.setString(2, customer.getPassword());
        preparedStatement.setString(3, customer.getSalt() );
        preparedStatement.setString(4, customer.getFirstName());
        preparedStatement.setString(5, customer.getLastName());
        preparedStatement.setDate(6, Date.valueOf(customer.getDateOfBirth()));
        preparedStatement.setInt(7, customer.getSocialSecurityNumber());
        preparedStatement.setString(8, customer.getAddress().getStreet());
        preparedStatement.setString(9, customer.getAddress().getzipcode());
        preparedStatement.setInt(10, customer.getAddress().getHouseNumber());
        preparedStatement.setString(11, customer.getAddress().getAddition());
        return preparedStatement;
    }

    @Override
    public Customer save(Customer customer) {
        logger.info("customer.save aangeroepen");
        jdbcTemplate.update(connection -> insertCustomer(customer, connection));
        return customer;
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
            int socialSecurityNumber = resultSet.getInt("socialSecurityNumber");
            String street = resultSet.getString("street");
            String zipcode = resultSet.getString("zipcode");
            int houseNumber = resultSet.getInt("housenumber");
            String addition = resultSet.getString("addition");

            Customer customer = new Customer(username, password, salt, firstName, lastName, dateOfBirth,
                    socialSecurityNumber, new Address(street, zipcode, houseNumber, addition));
            return customer;
        }
    }

    @Override
    public Customer findByUsername(String username) {
        String sql = "SELECT * from customer where username = ?";
        List<Customer> customerToFind = jdbcTemplate.query(sql, new CustomerRowMapper(), username);
        if(customerToFind.size() == 1) {
            return customerToFind.get(0);
        }
        return null;
    }
}