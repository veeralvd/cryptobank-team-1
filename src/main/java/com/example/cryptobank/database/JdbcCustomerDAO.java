package com.example.cryptobank.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcCustomerDAO {
    private final Logger logger = LoggerFactory.getLogger(JdbcCustomerDAO.class);
    private JdbcTemplate jdbcTemplate;

    public JdbcCustomerDAO() {
        logger.info("New JdbcCustomerDAO");
    }

}