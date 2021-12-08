package com.example.tubesp3b_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tubesp3b_2.databinding.ActivitySplashScreenBinding;
import com.example.tubesp3b_2.model.SharedPref;
import com.example.tubesp3b_2.view.IBoardingScreen;
import com.example.tubesp3b_2.view.LoginFragment;
import com.example.tubesp3b_2.view.OnBoarding1Fragment;
import com.example.tubesp3b_2.view.OnBoarding2Fragment;

public class SplashScreenActivity extends AppCompatActivity implements IBoardingScreen {
    private ActivitySplashScreenBinding binding;
    private SharedPref sp;

    //on-boarding page needs
    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inflating layout
        super.onCreate(savedInstanceState);
        this.binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        //attributes initialization --> sementara masih di set biar selalu show boarding screen
        this.sp = new SharedPref(getBaseContext());
        Log.e("Splash_ScreenTESTER", "onCreate: " +sp.getIndicator());
        sp.saveIndicator(-1);

        //set splash screen's animations
        this.binding.splashBg.animate().translationY(-2400).setDuration(1000).setStartDelay(4000);
        this.binding.logo.animate().translationY(2050).setDuration(1000).setStartDelay(4000);
        this.binding.appName.animate().translationY(2050).setDuration(1000).setStartDelay(4000);
        this.binding.lottieShuttle.animate().translationY(2050).setDuration(1000).setStartDelay(4000);

        //show / nah on boarding screen
        this.showBoardingScreen();
    }

    //decide to show on boarding screen or nah
    public void showBoardingScreen(){

        //first time running this app --> show on boarding screen
        if(this.sp.getIndicator() == -1){
            //set indicator --> on boarding screen wont show next time
            this.sp.saveIndicator(1);

            //set on-boarding page's adapter
            this.viewPager = this.binding.pager;
            this.pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            this.pagerAdapter.setActivity(this);
            this.viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setAdapter(pagerAdapter);
                }
            }, 5000);

        //the n-th time opening this app --> login page
        }else{
            this.changeBoardingPage(2);
        }
    }

    @Override
    //method untuk langsung pindah ke login page
    public void changeBoardingPage(int page) {
        this.viewPager.setCurrentItem(page);

//        //intent change : SplashScreenActivity to MainActivity
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 5050);
    }

    //inner class change on-boarding fragments
    //deprecated: as for now its fine to use
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{
        private SplashScreenActivity activity;

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void setActivity(SplashScreenActivity activity){
            this.activity = activity;
        }

        @NonNull
        @Override
        //return next boarding page
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OnBoarding1Fragment tab1 = OnBoarding1Fragment.newInstance(this.activity);
                    return tab1;
                case 1:
                    OnBoarding2Fragment tab2 = OnBoarding2Fragment.newInstance(this.activity);
                    return tab2;
                case 2:
                    LoginFragment tab3 = new LoginFragment();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}