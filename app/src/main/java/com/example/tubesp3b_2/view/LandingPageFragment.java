package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.databinding.LandingPageFragmentBinding;
import com.example.tubesp3b_2.databinding.LoginFragmentBinding;

public class LandingPageFragment extends Fragment implements View.OnClickListener {
    private LandingPageFragmentBinding binding;

    //must-have empty constructor
    public LandingPageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = LandingPageFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        this.binding.tvLandingPage.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        this.binding.logoLandingPage.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        this.binding.tvSlogan.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        this.binding.tvInformasi.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        this.binding.btnPesanSekarang.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();


        this.binding.btnPesanSekarang.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
    //binding.btnPesanSekarang menuju ke PesanFragment
    }
}
