package com.example.tubesp3b_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tubesp3b_2.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        //set splash screen's animations
        this.binding.splashBg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        this.binding.logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        this.binding.appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        this.binding.lottieShuttle.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        // intent change SplashScreenActivity to MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5050);
    }
}