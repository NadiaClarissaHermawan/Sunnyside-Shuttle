package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private LoginFragmentBinding binding;

    //must-have empty constructor
    public LoginFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = LoginFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        this.binding.tvLoginTitle.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        this.binding.etLoginUsername.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        this.binding.etLoginPassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        this.binding.btnLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        this.binding.btnLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.binding.btnLogin.getId()) {

        }
    }
}
