package com.example.tubesp3b_2.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.SplashScreenActivity;
import com.example.tubesp3b_2.databinding.LoginFragmentBinding;
import com.example.tubesp3b_2.model.room_database.AppDataBase;
import com.example.tubesp3b_2.presenter.PostLoginTask;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private LoginFragmentBinding binding;
    private ConnectivityManager connectivityManager;
    private Context context;
    private SplashScreenActivity activity;
    private AppDataBase dataBase;


    //must-have empty constructor
    public LoginFragment(){}


    //singleton
    public static LoginFragment newInstance(ConnectivityManager connectivityManager, Context context, SplashScreenActivity activity, AppDataBase dataBase){
        LoginFragment frag = new LoginFragment();
        frag.connectivityManager = connectivityManager;
        frag.activity = activity;
        frag.context = context;
        frag.dataBase = dataBase;

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = LoginFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //set login click listener
        this.binding.btnLogin.setOnClickListener(this::onClick);

        //hide loading gif
        this.binding.lottieLoading.setVisibility(View.GONE);

        return view;
    }


    @Override
    public void onClick(View view) {
        //login button
        if(view == this.binding.btnLogin) {
            //take input value
            String uname = this.binding.etLoginUsername.getText().toString();
            String pass = this.binding.etLoginPassword.getText().toString();

            //uname is empty
            if(uname.equals("")){
                this.binding.error.setText("Please fill your username..");
            //uname not empty
            }else{
                this.binding.error.setText("");

                //pass is empty
                if(pass.equals("")){
                    //make & post http request
                    this.binding.error.setText("Please fill your password..");
                }else{
                    this.binding.error.setText("");
                    //make & post http request
                    new PostLoginTask(this.context, this.activity, this.dataBase).execute(uname, pass);

                    //loading gif appear
                    this.binding.lottieLoading.setVisibility(View.VISIBLE);

                    //disable button (prevent spam :))
                    this.binding.btnLogin.setEnabled(false);
                }
            }
        }
    }


    //login failed
    public void loginFailed(){
        this.binding.lottieLoading.setVisibility(View.GONE);
        this.binding.btnLogin.setEnabled(true);
        this.binding.error.setText("Wrong Password !");
    }
}
