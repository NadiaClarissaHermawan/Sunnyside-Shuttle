package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.databinding.OnBoarding2FragmentBinding;

public class OnBoarding2Fragment extends Fragment implements View.OnClickListener{
    private OnBoarding2FragmentBinding binding;
    private IBoardingScreen ui;

    //must-have empty constructor
    public OnBoarding2Fragment(){}


    //singleton
    public static OnBoarding2Fragment newInstance(IBoardingScreen ui){
        OnBoarding2Fragment frag = new OnBoarding2Fragment();
        frag.ui = (IBoardingScreen) ui;

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = OnBoarding2FragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //set click listener
        this.binding.skipBtn.setOnClickListener(this::onClick);
        this.binding.boardingBack.setOnClickListener(this::onClick);
        this.binding.boardingNext.setOnClickListener(this::onClick);
        return view;
    }


    @Override
    public void onClick(View view) {
        if(view == this.binding.skipBtn || view == this.binding.boardingNext){
            this.ui.changeBoardingPage(2);
        }else if(view == this.binding.boardingBack){
            this.ui.changeBoardingPage(0);
        }
    }
}
