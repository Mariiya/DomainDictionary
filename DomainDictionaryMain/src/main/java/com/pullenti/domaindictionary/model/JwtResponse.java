package com.pullenti.domaindictionary.model;

public class JwtResponse {

    private String token;
    private User user;

    public JwtResponse(String jwt,User user) {
        this.token = jwt;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
