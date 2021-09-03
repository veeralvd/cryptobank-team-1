package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class AdminController {

    private AdminService adminService;


    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        logger.info("New AdminController");
    }
//
//    @PutMapping("/admin/login")
//    ResponseEntity<?> login(@RequestParam String username, String password) {
//        logger.info("login admin aangeroepen");
//
//
//        Admin adminToLogin = adminService.login(username, password);
//        if (adminToLogin.getSalt() != null) {
//            return ResponseEntity.ok(adminToLogin.toString());
//        } else {
//            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
//        }
//    }

    @PutMapping("/admin/register")
    public ResponseEntity<?> register(@RequestParam String username, String password) {
        logger.info("registerAdmin aangeroepen");
        try {
            Admin adminToRegister = adminService.register(username, password);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.AUTHORIZATION, adminToRegister.getToken());
            return new ResponseEntity<>(adminToRegister.getToken(),httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin")''
    public ResponseEntity<String> getAdmin(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {

        System.out.println(token);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("poep"));
        responseHeaders.set(HttpHeaders.AUTHORIZATION, token);

        Admin testAdmin = new Admin("stankie", "passwordje");
        if (token == null) {
            return new ResponseEntity<String>(testAdmin.toString(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<String>(testAdmin.toString(), responseHeaders, HttpStatus.OK);
    }

    @PutMapping("/admin/login")
    ResponseEntity<?> login(@RequestParam String username, String password) {
        logger.info("login admin aangeroepen");


        Admin adminToLogin = adminService.login(username, password);
        if (adminToLogin.getSalt() != null) {
            return ResponseEntity.ok(adminToLogin.toString());
        } else {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
    }
}