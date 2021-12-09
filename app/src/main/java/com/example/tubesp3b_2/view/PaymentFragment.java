package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.R;
import com.example.tubesp3b_2.databinding.PaymentFragmentBinding;

public class PaymentFragment extends Fragment implements View.OnClickListener {
    private PaymentFragmentBinding binding;

    //must-have empty constructor
    public PaymentFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = PaymentFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        this.binding.btnPesan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        //binding.btnPesan menuju ke Konfirmasi Berhasil (dialog/toast/snackbar)
    }
}
