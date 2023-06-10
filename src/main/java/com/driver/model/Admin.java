package com.driver.model;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Admin{
    public Admin() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int adminId;

    String username;

    String password;

    public Admin(int adminId, String userName, String password) {
        this.adminId = adminId;
        this.username = userName;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}