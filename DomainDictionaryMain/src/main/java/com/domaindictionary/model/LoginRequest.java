package com.domaindictionary.model;

public class LoginRequest {
    private String nameOrEmail;
    private String password;

    public LoginRequest(String nameOrEmail, String password) {
        this.nameOrEmail = nameOrEmail;
        this.password = password;
    }

    public String getNameOrEmail() {
        return nameOrEmail;
    }

    public void setNameOrEmail(String nameOrEmail) {
        this.nameOrEmail = nameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
