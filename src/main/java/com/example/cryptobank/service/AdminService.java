package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {


    private RootRepository rootRepository;
    private LoginService loginService;


    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New AdminService");
    }


    public Admin register(String username, String password) {
        Admin attemptToRegister = new Admin(username, password);
        Admin adminInDatabase = findByUsername(username);

        if(adminInDatabase == null || attemptToRegister.getUsername().equals(adminInDatabase.getUsername())) {
            String salt = new Saltmaker().generateSalt();attemptToRegister.setPassword(HashHelper.hash(password, salt, PepperService.getPepper()));
            attemptToRegister.setSalt(salt);
            Admin registredAdmin = rootRepository.save(attemptToRegister);
            return registredAdmin;
        }
        return attemptToRegister;
    }

    public Admin login(String username, String password) {
        Admin adminAttempToLogin = loginService.login(username, password);
        return adminAttempToLogin;
    }

    public boolean authenticate(String hashInDatabase, String hashedPassword) {
        return hashInDatabase.equals(hashedPassword);
    }

    private Admin findByUsername(String username) {
        Admin adminToCheck = rootRepository.findByUsername(username);
        return adminToCheck;
    }



}