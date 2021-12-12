package com.example.tubesp3b_2.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.model.RoutesResult;
import com.example.tubesp3b_2.model.TicketOrder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class GetCoursesTask {
    //attributes
    private final String BASE_URL = "https://devel.loconode.com/pppb/v1/courses?";
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

        String url = BASE_URL + "source=" + order.getSource() + "&destination=" + order.getDestination() +
                "&vehicle=" + order.getVehicle()+ "&date="+ order.getDate() + "&hour=" + order.getHour();

        Log.e("TEST_TOKEN_GETCOURSES", "executeCourses: "+this.accesstoken );

        this.callVolleyCourses(url);
    }


    //call the web service w/ Volley Library (GET)
    //TODO: ERROR 400, E_NO_TOKEN (FIX THIS)
    public void callVolleyCourses(String url){
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ROUTES_REQ_SUCCEED", "onSucceedResponse: "+ response.toString());
                        processResultCourses(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ROUTES_REQ_ERROR", "onErrorResponse: "+error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String auth = "Bearer "+accesstoken;
                headers.put("Authorization", auth);
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
        RoutesResult res = gson.fromJson(json, RoutesResult.class);

        //return response to mainActivity
        this.mainActivity.giveRoutesResponse(res);
    }
}
