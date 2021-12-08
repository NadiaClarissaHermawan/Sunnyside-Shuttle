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

        this.binding.btnLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.binding.btnLogin.getId()) {

        }
    }
}
