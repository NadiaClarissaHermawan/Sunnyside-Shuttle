package com.example.tubesp3b_2.presenter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class Login_ThreadView implements Runnable{
    private Thread thread;
    private Login_UIHandler handler;

    //penampung input user
    private String uname, pass;

    //reading response from http login request needs
    private BufferedReader reader;
    private String line;
    private StringBuffer responseContent;

    //connection set up to API needs
    private static HttpURLConnection connection;

    //constructor
    public Login_ThreadView(Login_UIHandler handler){
        this.handler = handler;
        this.thread = new Thread(this);
        this.responseContent = new StringBuffer();
    }

    //enkapsulasi run thread
    public void startThread(String uname, String pass){
        this.uname = uname;
        this.pass = pass;
        this.thread.start();
    }

    @Override
    //post method to send uname & password
    public void run() {
        String finalResult = "";
        try{
            //set target URL
            URL url = new URL("https://devel.loconode.com/pppb/v1/authenticate");

            //open connection
            connection = (HttpURLConnection) url.openConnection();

            //request setup
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");

            //prepare data as requested (JSON)
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("username", this.uname);
                jsonObject.put("password", this.pass);
            }catch (JSONException e){
                e.printStackTrace();
            }
            Log.e("LOGIN_TESTER", "run: "+jsonObject.toString() );

            //send request to API
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(jsonObject.toString());
            dos.flush();
            dos.close();

            //connect it
            connection.connect();

            //get response code (200 == OK, 400 == MEH)
            int status = connection.getResponseCode();
            Log.e("STATTS", "run: "+status );

            //if OK
            if(status < 299){
                //ambil response dari API via buffered reader (berupa JSON), pindahin ke string builder.
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((this.line = reader.readLine()) != null){
                    stringBuilder.append(this.line+"\n");
                }
                String res = stringBuilder.toString();

                Log.e("TESTER RES", "run: "+res);

                //ambil string input yg diinginkan keynya
                try{
                    jsonObject = new JSONObject(res);
                    finalResult = jsonObject.getJSONObject("token").toString();
                }catch (JSONException e){
                    e.printStackTrace();
                }

                //move to landing page
                this.handler.getToken(finalResult);

            //if NOT OK
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line + ", ");
                }
                reader.close();
            }
            Log.e("DEBUG", "run: "+ responseContent.toString());

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
    }
}
