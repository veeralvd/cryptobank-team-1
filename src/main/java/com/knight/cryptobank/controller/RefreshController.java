package com.knight.cryptobank.controller;

import com.knight.cryptobank.domain.Admin;
import com.knight.cryptobank.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RefreshController {
    private final Logger logger = LoggerFactory.getLogger(RefreshController.class);
    private AdminService adminService;

    @Autowired
    public RefreshController(AdminService adminService) {
        this.adminService = adminService;
        logger.info("New RefreshController");
    }

    @GetMapping("/admin/refresh")
    public ResponseEntity<?> refreshAdminToken(@RequestHeader("Authorization") String token) {
        logger.info("admin refresh token aangeroepen");
        Admin adminToRefreshToken = adminService.authenticate(token);
        if (adminToRefreshToken != null) {
            adminService.refresh(adminToRefreshToken);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set(HttpHeaders.AUTHORIZATION, adminToRefreshToken.getAccessToken());
            responseHeader.set("refresh_token", adminToRefreshToken.getRefreshToken());
            return new ResponseEntity<String>(adminToRefreshToken.getAccessToken(), responseHeader, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("optyfen gauw, nu echt.", HttpStatus.FORBIDDEN);
        }
    }

}