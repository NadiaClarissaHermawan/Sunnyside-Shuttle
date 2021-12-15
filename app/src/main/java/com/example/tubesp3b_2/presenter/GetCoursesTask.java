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
import com.example.tubesp3b_2.model.result.CoursesResult;
import com.example.tubesp3b_2.model.TicketOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class GetCoursesTask {
    //attributes
    private final String BASE_URL = "https://devel.loconode.com/pppb/v1/courses";
    private Context context;
    private MainActivity mainActivity;
    private Gson gson;
    private String accesstoken;

    //constructor
    public GetCoursesTask(Context context, MainActivity activity, String accesstoken){
        this.context = context;
        this.mainActivity = activity;
        this.accesstoken = accesstoken;
    }


    //start Volley Thread
    public void executeCourses(TicketOrder order){
        //initialize GSON builder & clean input
        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();

        String url = BASE_URL + "?source=" + order.getSource() + "&destination=" + order.getDestination() +
                "&vehicle=" + order.getVehicle()+ "&date="+ order.getDate() + "&hour=" + order.getHour();

        this.callVolleyCourses(url);
    }


    //call the web service w/ Volley Library (GET)
    public void callVolleyCourses(String url){
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("COURSES_REQ_SUCCEED", "onSucceedResponse: "+ response.toString());
                    processResultCourses(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("COURSES_REQ_ERROR", "onErrorResponse: "+error.toString());
                }
        }) {
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
        volleyRequestQueue.add(stringRequest);
    }


    //if callVoley succeed, call this response handler
    public void processResultCourses(String json){
        //convert String json to result Object
        CoursesResult res = gson.fromJson(json, CoursesResult.class);

        //return response to mainActivity
        this.mainActivity.giveCoursesResponse(res);
    }
}
