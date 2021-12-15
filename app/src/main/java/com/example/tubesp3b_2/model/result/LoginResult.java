package com.example.tubesp3b_2.model.result;

public class LoginResult {
    private String token, message, uname;

    public LoginResult(String token, String message){
        this.token = token;
        this.message = message;
    }

    public void setUname(String uname){
        this.uname = uname;
    }

    public String getToken(){
        return this.token;
    }

    public String getUname(){
        return this.uname;
    }
}
