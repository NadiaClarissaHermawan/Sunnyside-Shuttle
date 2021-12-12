package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.databinding.SeatFragmentBinding;
import com.example.tubesp3b_2.model.TicketOrder;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.presenter.GetCoursesTask;

//TODO: lanjutin lengkapin
public class SeatFragment extends Fragment implements View.OnClickListener {
    private SeatFragmentBinding binding;
    private MainActivity activity;
    private FragmentManager fragmentManager;
    private User user;
    private TicketOrder order;

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

        //listener get ticket order details
        this.fragmentManager.setFragmentResultListener(
            "getOrderDetails", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    order = new TicketOrder(result.getString("source"), result.getString("destination"),
                            result.getString("vehicle"), result.getString("date"), result.getString("hour"));

                    Log.e("TESTER", "onFragmentResult: "+order.getHour());
                    Log.e("TESTER", "onFragmentResult: "+order.getDate());
                    Log.e("TESTER", "onFragmentResult: "+order.getVehicle());
                    Log.e("TESTER", "onFragmentResult: "+order.getDestination());
                    Log.e("TESTER", "onFragmentResult: "+order.getSource());

                    //get courses data from API
                    new GetCoursesTask(getContext(), activity, user.getToken()).executeCourses(order);
                }
            }
        );

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}
