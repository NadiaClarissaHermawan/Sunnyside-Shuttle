package com.example.tubesp3b_2.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.tubesp3b_2.SplashScreenActivity;

public class Login_UIHandler extends Handler {
    private final static int AUTHENTICATION_SUCCEED = 0;
    private SplashScreenActivity activity;

    public Login_UIHandler(SplashScreenActivity activity){
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg){
        if(msg.what == AUTHENTICATION_SUCCEED){

            //antisipasi kalau kena "Only the original thread can --"
            this.activity.runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    activity.changeIntent();
                }
            });
        }
    }

    public void getToken(String token){
        Message msg = new Message();
        msg.what = AUTHENTICATION_SUCCEED;
        msg.obj = token;
        this.handleMessage(msg);
    }
}
