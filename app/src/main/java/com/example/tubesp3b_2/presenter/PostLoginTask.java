package com.example.tubesp3b_2.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubesp3b_2.SplashScreenActivity;
import com.example.tubesp3b_2.model.request.LoginInput;
import com.example.tubesp3b_2.model.result.LoginResult;
import com.example.tubesp3b_2.model.room_database.AppDataBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//pemanggilan webservice with volley library
public class PostLoginTask {
    //attributes
    private final String BASE_URL = "https://devel.loconode.com/pppb/v1/authenticate";
    private Context context;
    private SplashScreenActivity activity;
    private Gson gson;
    private String uname;
    private AppDataBase dataBase;

    //constructor
    public PostLoginTask(Context context, SplashScreenActivity activity, AppDataBase dataBase){
        this.context = context;
        this.activity = activity;
        this.dataBase = dataBase;
    }


    //start Volley Thread
    public void execute(String uname, String pass){
        this.uname = uname;

        //initialize GSON builder & clean input
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();

        //create input Object
        LoginInput input = new LoginInput(uname, pass);

        //convert input object into Json String
        String inputGson = gson.toJson(input);
        try{
            //convert Json String into Json Object
            JSONObject jsonObject = new JSONObject(inputGson);
            this.callVolley(jsonObject);

            Log.e("LOGIN_REQ_BODY", "http request body (input) : "+jsonObject.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    //call the web service w/ Volley Library (POST)
    public void callVolley(JSONObject json){
        //call web service & create listener
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                processResult(response.toString());
                Log.e("LOGIN_REQ_SUCCEED", "onSucceedResponse: "+ response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN_REQ_ERROR", "onErrorResponse: "+error.toString());
            }
        }){
            //set http request header
            protected Map<String, String> getHeader() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        //send request to API
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(this.context);
        volleyRequestQueue.add(jsonRequest);
    }


    //if callVoley succeed, call this response handler
    public void processResult(String json){
        //convert String json to result Object
        LoginResult res = gson.fromJson(json, LoginResult.class);
        res.setUname(this.uname);

        //return response to splashScreenActivity
        this.activity.changeIntent(res.getUname(), res.getToken());
    }
}
