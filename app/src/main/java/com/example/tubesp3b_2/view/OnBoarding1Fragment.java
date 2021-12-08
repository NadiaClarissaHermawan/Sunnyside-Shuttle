package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.databinding.OnBoarding1FragmentBinding;

public class OnBoarding1Fragment extends Fragment implements View.OnClickListener{
    private OnBoarding1FragmentBinding binding;
    private IBoardingScreen ui;

    //must-have empty constructor
    public OnBoarding1Fragment(){}

    //singleton
    public static OnBoarding1Fragment newInstance(IBoardingScreen ui){
        OnBoarding1Fragment frag = new OnBoarding1Fragment();
        frag.ui = (IBoardingScreen) ui;

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = OnBoarding1FragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //set click listener
        this.binding.skipBtn.setOnClickListener(this::onClick);
        this.binding.boardingNext.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onClick(View view) {
        //go to login page
        if(view == this.binding.skipBtn){
            this.ui.changeBoardingPage(2);
        //go to boarding page 1
        }else if(view == this.binding.boardingNext){
            this.ui.changeBoardingPage(1);
        }
    }
}
