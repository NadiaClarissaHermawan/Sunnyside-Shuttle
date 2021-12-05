package com.example.tubesp3b_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.tubesp3b_2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inflating layout
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        //inisialisasi atribut
        this.fragmentManager = this.getSupportFragmentManager();
    }
    //testing commit and push
}