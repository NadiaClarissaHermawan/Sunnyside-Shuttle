package com.example.tubesp3b_2.presenter;

import android.util.Log;

import com.example.tubesp3b_2.SplashScreenActivity;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.model.room_database.AppDataBase;
import com.example.tubesp3b_2.view.interfaces.TaskDao;

import java.util.List;

public class LoginCheckerThread implements Runnable{
    private Thread thread;
    private AppDataBase dataBase;
    private TaskDao taskDao;
    private SplashScreenActivity activity;
    private int login_indicator, task_code;
    private String uname, token;

    public LoginCheckerThread(AppDataBase dataBase, SplashScreenActivity activity){
        this.thread = new Thread(this);
        this.dataBase = dataBase;
        this.activity = activity;
        this.taskDao = this.dataBase.taskDao();
    }


    public int getLogin_indicator(){
        return this.login_indicator;
    }


    public void addUser(String uname, String token){
        this.uname = uname;
        this.token = token;
        this.task_code = 1;
        this.thread.start();
    }


    public void removeUser(){
        this.task_code = 2;
        this.thread.start();
    }


    public void startThread(){
        this.task_code = 0;
        this.thread.start();
    }


    @Override
    public void run() {
        //check
        if(this.task_code == 0){
            List<User> users = this.taskDao.getUser();

            //login is necessary
            if(users.isEmpty()){
                this.login_indicator = 0;
                this.activity.getResultCheckUser("", "");
            //nope
            }else{
                this.login_indicator = 1;
                this.activity.getResultCheckUser(users.get(0).getUsername(), users.get(0).getToken());
            }
        //log in
        }else if(this.task_code == 1){
            this.login_indicator= 1;
            this.taskDao.insert(new User(0, this.uname, this.token));
        //sign out
        }else{
            this.login_indicator = 0;
            this.taskDao.delete();
            this.activity.getResultCheckUser("", "");
        }
    }
}
