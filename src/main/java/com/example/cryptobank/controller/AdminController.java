package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private AdminService adminService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        logger.info("New AdminController");
    }

    @PutMapping("/admin/register")
    public Admin register(@RequestParam String username, String password) {
        Admin adminToRegister = adminService.register(username, password);
        logger.info("registerAdmin aangeroepen");
        return adminToRegister;

    }

    @GetMapping("/admin")
    public Admin getAdmin() {
        Admin testAdmin = new Admin("stankie", "passwordje"
        );
        logger.info("getAdmin aangeroepen");
        return testAdmin;
    }

}