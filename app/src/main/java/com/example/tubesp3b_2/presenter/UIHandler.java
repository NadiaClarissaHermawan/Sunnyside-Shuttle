package com.example.tubesp3b_2.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tubesp3b_2.SplashScreenActivity;

public class UIHandler extends Handler {
    private final static int AUTHENTICATION_SUCCEED = 0;
    private SplashScreenActivity activity;

    public UIHandler(SplashScreenActivity activity){
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg){
        if(msg.what == AUTHENTICATION_SUCCEED){

            //antisipasi kalau kena "Only the original thread can --"
            this.activity.runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    activity.changeIntent((String)msg.obj);
                }
            });
        }
    }

    //ambil hasil token dari thread
    public void getToken(String token){
        Message msg = new Message();
        msg.what = AUTHENTICATION_SUCCEED;
        msg.obj = token;
        this.handleMessage(msg);
    }
}
