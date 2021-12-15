package com.example.tubesp3b_2.presenter;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.model.request.ConfirmedOrderInput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//pemanggilan webservice with volley library
public class PostOrderTask {
    //attributes
    private final String BASE_URL = "https://devel.loconode.com/pppb/v1/orders";
    private Context context;
    private MainActivity activity;
    private Gson gson;
    private String accesstoken;

    //constructor
    public PostOrderTask(Context context, MainActivity activity, String accesstoken){
        this.context = context;
        this.activity = activity;
        this.accesstoken = accesstoken;
    }


    //start Volley Thread
    public void execute(String course_id, String seats){
        //initialize GSON builder & clean input
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();

        //create input Object
        ConfirmedOrderInput input = new ConfirmedOrderInput(course_id, seats);

        //convert input object into Json String
        String inputGson = gson.toJson(input);
        try{
            //convert Json String into Json Object
            JSONObject jsonObject = new JSONObject(inputGson);
            this.callVolley(jsonObject);

            Log.e("POST_ORDER_BODY", jsonObject.toString());
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
                Log.e("POST_ORDER_SUCCEED", "onSucceedResponse: "+ response.toString());
                processResult(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("POST_ORDER_ERROR", "onErrorResponse: "+error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("Authorization", "Bearer " + accesstoken);
                return headers;
            }
        };

        //send request to API
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(this.context);
        volleyRequestQueue.add(jsonRequest);
    }


    //if callVoley succeed, call this response handler
    public void processResult(String json){
        //back to payment page
        this.activity.givePostOrderResponse();
    }
}