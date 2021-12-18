package com.example.tubesp3b_2.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.databinding.PaymentFailedDialogBinding;
import com.example.tubesp3b_2.databinding.PaymentFragmentBinding;
import com.example.tubesp3b_2.databinding.PaymentSucceedDialogBinding;
import com.example.tubesp3b_2.model.TicketOrder;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.presenter.PostOrderTask;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class PaymentFragment extends Fragment implements View.OnClickListener {
    private PaymentFragmentBinding binding;
    private FragmentManager fragmentManager;
    private MainActivity activity;
    private User user;
    private TicketOrder order;
    private Spinner spinnerDiscount;
    private int rawTotal;

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

        //generate discount spinner
        this.setupSpinnerDiscount();

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
        this.binding.tvFee.setText("Each seat fee : " + this.order.getFee());
        this.binding.tvJumlahTicket.setText(this.order.getSeats().size() + "x Ticket(s)");
        this.binding.tvTicketSeat.setText(this.formatSeats());
        this.rawTotal = this.order.getSeats().size() * this.order.getFee();
        this.binding.tvTotalPayment.setText("Rp "+this.rawTotal);
        this.binding.discountAmount.setText("Rp 0");
        this.binding.finalTotalAmount.setText("Rp "+this.rawTotal);
    }


    //setup discounts spinner
    public void setupSpinnerDiscount(){
        //adding discounts
        ArrayList<String> discounts = new ArrayList<>();
        discounts.add("No discount choosen");
        discounts.add("Diskon Gebyar Akhir Tahun 50%");
        discounts.add("Diskon 20% Tiket HepiHepi 2021");

        //setup spinner
        this.spinnerDiscount = (Spinner) this.binding.spinnerDiscount;
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this.getContext(), android.R.layout.simple_spinner_dropdown_item, discounts);
        spinnerDiscount.setAdapter(adp);
        spinnerDiscount.setSelected(true);

        //setup onselect listener
        this.setupSpinnerListener();
    }


    //setup spinner listener
    public void setupSpinnerListener(){
        //set onselect listener
        this.spinnerDiscount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentDiscount = binding.spinnerDiscount.getSelectedItem().toString();
                //update final total
                if(currentDiscount.equals("No discount choosen")){
                    binding.discountAmount.setText("Rp 0");
                    binding.finalTotalAmount.setText("Rp "+rawTotal);
                }else if(currentDiscount.equals("Diskon Gebyar Akhir Tahun 50%")){
                    binding.discountAmount.setText("Rp "+(int)(0.5*rawTotal));
                    binding.finalTotalAmount.setText("Rp "+(int)(rawTotal-(0.5*rawTotal)));
                }else{
                    binding.discountAmount.setText("Rp "+(int)(0.2*rawTotal));
                    binding.finalTotalAmount.setText("Rp "+(int)(rawTotal-(0.2*rawTotal)));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
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
        this.binding.btnConfirmPayment.setEnabled(false);
        new PostOrderTask(this.getContext(), this.activity, this.user.getToken()).execute(this.order.getCourse_id(), formatSeats().replaceAll("\\s","").replaceAll("s", ""));
    }


    //enabling button
    public void enableButton(){
        this.binding.btnConfirmPayment.setEnabled(true);
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
