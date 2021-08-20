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

    public Admin register(String  username, String password) {
        Admin adminToRegister = new Admin(username, password);
        adminDAO.save(adminToRegister);
        return adminToRegister;
    }

}