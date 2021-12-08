package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.R;
import com.example.tubesp3b_2.databinding.PembayaranFragmentBinding;
import com.example.tubesp3b_2.databinding.PesanFragmentBinding;

public class PembayaranFragment extends Fragment implements View.OnClickListener {
    private PembayaranFragmentBinding binding;

    //must-have empty constructor
    public PembayaranFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = PembayaranFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        this.binding.btnPesan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        //binding.btnPesan menuju ke Konfirmasi Berhasil (dialog/toast/snackbar)
    }
}
