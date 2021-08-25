package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RootRepository {
    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);
    private AdminDAO adminDAO;
    private CustomerDAO customerDAO;

    @Autowired
    public RootRepository(AdminDAO adminDAO, CustomerDAO customerDAO) {
        this.adminDAO = adminDAO;
        this.customerDAO = customerDAO;
        logger.info("New RootRepository");
    }

    public Admin getAdminByUsername(String username) {
        Admin admin = adminDAO.findByUsername(username);
        return admin;
    }

    public Admin save(Admin admin) {
        return adminDAO.save(admin);
    }

}