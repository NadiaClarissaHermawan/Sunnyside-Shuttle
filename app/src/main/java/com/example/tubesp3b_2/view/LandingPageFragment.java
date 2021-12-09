package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.databinding.LandingPageFragmentBinding;
import com.example.tubesp3b_2.databinding.LoginFragmentBinding;
import com.example.tubesp3b_2.model.User;

public class LandingPageFragment extends Fragment implements View.OnClickListener {
    private LandingPageFragmentBinding binding;
    private User user;

    //must-have empty constructor
    public LandingPageFragment(){}

    //singleton
    public static LandingPageFragment newInstance(User user){
        LandingPageFragment fragment = new LandingPageFragment();
        fragment.user = user;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = LandingPageFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //set username
        this.binding.username.setText(this.user.getUsername());

        //set listener
        this.binding.bookATicket.setOnClickListener(this::onClick);

        return view;
    }


    @Override
    public void onClick(View view) {
        //book a ticket
        if(view == this.binding.bookATicket){
            Bundle nextPage = new Bundle();
            nextPage.putInt("page", 1);
            this.getParentFragmentManager().setFragmentResult("changePage", nextPage);
        }
    }
}
