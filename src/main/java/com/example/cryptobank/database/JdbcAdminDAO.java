package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class JdbcAdminDAO implements AdminDAO {

    private final Logger logger = LoggerFactory.getLogger(JdbcAdminDAO.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAdminDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcAdminDAO");
    }

    // TODO: 20/08/2021 Ik kreeg het niet helemaal spits om een userdao inteace te programmeren voor zowel een admin als een customer
    // het ging mis bij het implementeren van deze klasse. Daar wilde de methode save() alleen een user als parameter en geen admin
    // om die reden heb ik het aangepast naar een adminDao interace. hoewel ik nu wel het gevoel heb dat die interace vrij nutteloos is geworden maagroed.

    private PreparedStatement insertAdminStatement(Admin admin, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into admin (username, password) values (?, ?)"
        );
        preparedStatement.setString(1, admin.getUsername());
        preparedStatement.setString(2, admin.getPassword());
        return preparedStatement;
    }

    @Override
    public Admin save(Admin admin) {
        logger.debug("adminDao.save aangeroepen");
        jdbcTemplate.update(connection -> insertAdminStatement(admin, connection));
        return admin;
    }



    @Override
    public Admin findByUsername(String username) {
        return null;
    }
}