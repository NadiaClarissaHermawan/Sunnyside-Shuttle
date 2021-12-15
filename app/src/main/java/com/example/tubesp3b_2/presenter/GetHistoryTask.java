package com.example.tubesp3b_2.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.model.result.HistoriesResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class GetHistoryTask {
    //attributes
    private final String BASE_URL = "https://devel.loconode.com/pppb/v1/orders?limit=10&offset=1";
    private Context context;
    private MainActivity mainActivity;
    private Gson gson;
    private String accesstoken;

    //constructor
    public GetHistoryTask(Context context, MainActivity activity, String accesstoken){
        this.context = context;
        this.mainActivity = activity;
        this.accesstoken = accesstoken;
    }


    //start Volley Thread --> no param this time
    public void executeHistory(){
        //initialize GSON builder & clean input
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();

        this.callVolleyHistory();
    }


    //call the web service w/ Volley Library (GET)
    public void callVolleyHistory(){
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HISTORY_REQ_SUCCEED", "onSucceedResponse: "+ response.toString());
                        processResultCourses(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HISTORY_REQ_ERROR", "onErrorResponse: "+error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accesstoken);
                return headers;
            }
        };

        //send request to API
        if(this.context == null){
            Log.e("HISTORY TEST", "callVolleyHistory: " );
        }else {
            RequestQueue volleyRequestQueue = Volley.newRequestQueue(this.context);
            volleyRequestQueue.add(stringRequest);
        }
    }


    //if callVoley succeed, call this response handler
    public void processResultCourses(String json){
        //convert String json to result Object
        HistoriesResult res = gson.fromJson(json, HistoriesResult.class);

        //return response to mainActivity
        this.mainActivity.giveHistoryResponse(res);
    }
}
