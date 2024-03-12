package com.cstrien.thi_trac_nghiem.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User {
    private int id, role;
    private String name, password;

    public User(int role, String name, String password) {
        this.role = role;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
