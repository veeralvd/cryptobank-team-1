package com.knight.cryptobank.database;


import com.knight.cryptobank.domain.Admin;

public interface AdminDAO {

    Admin save(Admin admin);

    Admin findByUsername(String username);

    String findAdminUsernameByToken(String username);

    void insertTokenByAdminUsername(String username, String token);

}
