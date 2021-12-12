package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.databinding.SeatFragmentBinding;
import com.example.tubesp3b_2.model.User;

//TODO: lanjutin lengkapin
public class SeatFragment extends Fragment implements View.OnClickListener {
    private SeatFragmentBinding binding;
    private MainActivity activity;
    private FragmentManager fragmentManager;
    private User user;


    //must-have empty constructor
    public SeatFragment(){}


    //singleton
    public static SeatFragment newInstance(User user, FragmentManager fragmentManager, MainActivity activity){
        SeatFragment fragment = new SeatFragment();
        fragment.user = user;
        fragment.fragmentManager = fragmentManager;
        fragment.activity = activity;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = SeatFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //get courses data from API -->
        //new GetRoutes_n_CoursesTask(this.getContext(), this.activity, this.user.getToken()).executeCourses();

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}
