package com.example.tubesp3b_2.model;

public class User {
    private final String USERNAME, TOKEN;

    public User(String username, String token){
        this.USERNAME = username;
        this.TOKEN = token;
    }

    public String getUsername(){
        return this.USERNAME;
    }

    public String getToken(){
        return this.TOKEN;
    }
}
