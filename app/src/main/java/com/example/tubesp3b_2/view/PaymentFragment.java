package com.example.tubesp3b_2.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.R;
import com.example.tubesp3b_2.databinding.PaymentFailedDialogBinding;
import com.example.tubesp3b_2.databinding.PaymentFragmentBinding;
import com.example.tubesp3b_2.databinding.PaymentSucceedDialogBinding;
import com.example.tubesp3b_2.model.TicketOrder;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.presenter.PostOrderTask;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class PaymentFragment extends Fragment implements View.OnClickListener {
    private PaymentFragmentBinding binding;
    private FragmentManager fragmentManager;
    private MainActivity activity;
    private User user;
    private TicketOrder order;

    //must-have empty constructor
    public PaymentFragment(){}


    //singleton
    public static PaymentFragment newInstance(User user, FragmentManager fragmentManager, MainActivity activity){
        PaymentFragment fragment = new PaymentFragment();
        fragment.user = user;
        fragment.fragmentManager = fragmentManager;
        fragment.activity = activity;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = PaymentFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //set confirm btn listener
        this.binding.btnConfirmPayment.setOnClickListener(this::onClick);

        //listener get ticket order details
        this.fragmentManager.setFragmentResultListener(
            "getOrderConfirmation", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    order = (TicketOrder) Parcels.unwrap(result.getParcelable("confirmedOrder"));
                    updateView();
                }
            }
        );

        return view;
    }


    //update order details to layout view
    public void updateView(){
        this.binding.tvRutePayment.setText(this.order.getSource() + " to "+this.order.getDestination());
        this.binding.tvTanggalPayment.setText(this.order.getDate() + "   "+this.order.getHour()+":00");
        this.binding.tvVehicle.setText(this.order.getVehicle() + " car");
        this.binding.tvFee.setText("each seat fee : " + this.order.getFee());
        this.binding.tvJumlahTicket.setText(this.order.getSeats().size() + "x Ticket(s)");
        this.binding.tvTicketSeat.setText(this.formatSeats());
        this.binding.tvTotalPayment.setText("Rp "+this.order.getSeats().size() * this.order.getFee());
    }


    //formating seats string view
    public String formatSeats(){
        ArrayList<Integer> seats =  this.order.getSeats();
        Collections.sort(seats);
        int size = seats.size();

        String res = "";
        for(int i = 0; i< size; i++){
            if(i == 0){
                res = res + "s" + (seats.get(i)+1);
            }else{
                res = res + ", s" + (seats.get(i)+1);
            }
        }
        return res;
    }


    @Override
    public void onClick(View view) {
        new PostOrderTask(this.getContext(), this.activity, this.user.getToken()).execute(this.order.getCourse_id(), formatSeats().replaceAll("\\s","").replaceAll("s", ""));
    }


    //showing popup payment succeed
    public void paymentSucceed(){
        //bind dengan layout popupnya
        PaymentSucceedDialogBinding bindingPopup = PaymentSucceedDialogBinding.inflate(getLayoutInflater());
        View viewPopup = bindingPopup.getRoot();

        //bikin dialog window untuk popupny & set stylenya
        final Dialog addPopup = new Dialog(this.getActivity(), android.R.style.Theme);
        addPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));

        //masukin view layout xml untuk popupnya
        addPopup.setContentView(viewPopup);
        addPopup.setCancelable(true);

        //listener back to home button
        bindingPopup.backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //move to landing page
                Bundle nextPage = new Bundle();
                nextPage.putInt("page", 0);
                fragmentManager.setFragmentResult("changePage", nextPage);

                addPopup.dismiss();
            }
        });

        addPopup.show();
    }


    //showing popup payment failed
    public void paymentFailed(){
        //bind dengan layout popupnya
        PaymentFailedDialogBinding bindingPopup = PaymentFailedDialogBinding.inflate(getLayoutInflater());
        View viewPopup = bindingPopup.getRoot();

        //bikin dialog window untuk popupny & set stylenya
        final Dialog addPopup = new Dialog(this.getActivity(), android.R.style.Theme);
        addPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));

        //masukin view layout xml untuk popupnya
        addPopup.setContentView(viewPopup);
        addPopup.setCancelable(true);

        //listener pick another seats button
        bindingPopup.backToSeatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //move to Book Ticket fragment
                Bundle nextPage = new Bundle();
                nextPage.putInt("page", 1);
                fragmentManager.setFragmentResult("changePage", nextPage);

                addPopup.dismiss();
            }
        });

        addPopup.show();
    }
}
