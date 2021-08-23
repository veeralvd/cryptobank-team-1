package com.example.cryptobank.service;

import com.example.cryptobank.database.AdminDAO;
import com.example.cryptobank.domain.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminDAO adminDAO;

    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
        logger.info("New AdminService");
    }

    public boolean checkIfUserCanBeRegistred(String username) {
       Admin adminToCheck = adminDAO.findByUsername(username);
        return adminToCheck == null;
    }

    public Admin register(String  username, String password) {
        Admin adminToRegister = new Admin(username, password);
        if (checkIfUserCanBeRegistred(username)) {
            Admin registredAdmin = adminDAO.save(adminToRegister);
            return registredAdmin;
        }

        return adminToRegister;
    }

}