package com.example.tubesp3b_2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.parceler.Parcel;

@Parcel
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int user_id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "token")
    private String token;

    public User(){}

    public User(int user_id, String username, String token){
        this.user_id = user_id;
        this.username = username;
        this.token = token;
    }

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setToken(String token){
        this.token = token;
    }

    public int getUser_id(){
        return this.user_id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getToken(){
        return this.token;
    }
}
