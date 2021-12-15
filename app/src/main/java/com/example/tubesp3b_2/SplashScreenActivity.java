package com.example.tubesp3b_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.tubesp3b_2.databinding.ActivitySplashScreenBinding;
import com.example.tubesp3b_2.model.result.LoginResult;
import com.example.tubesp3b_2.model.SharedPref;
import com.example.tubesp3b_2.view.interfaces.IBoardingScreen;
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

        //set splash screen's animations
        this.binding.splashBg.animate().translationY(-2400).setDuration(1000).setStartDelay(4000);
        this.binding.logo.animate().translationY(2050).setDuration(1000).setStartDelay(4000);
        this.binding.appName.animate().translationY(2050).setDuration(1000).setStartDelay(4000);
        this.binding.lottieShuttle.animate().translationY(2050).setDuration(1000).setStartDelay(4000);

        //setup status bar
        this.setupStatusBar(0);

        //show / nah on boarding screen
        this.showBoardingScreen();
    }


    //set status bar
    public void setupStatusBar(int page){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //ganti warna status bar --> biru
        if(page == 0){
            window.setStatusBarColor(this.getResources().getColor(R.color.light_blue));
        //ganti balik warna status bar jadi putih
        }else{
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
    }


    //decide to show on boarding screen or nah
    //TODO: FIX FIRST TIME ON BOARDING SCREEN
    public void showBoardingScreen(){
        //first time running this app
        if(this.sp.getIndicator("first_time_install") == 0){
            //TODO: LATER CHANGE THE INDICATOR INTO 1
            this.sp.saveIndicator(0, "first_time_install");

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
            this.sp.saveIndicator(0, "first_time_install");
            //TODO THIS PART IS STILL OVERLAPPING WITH THE SPLASH SCREEN, CAN WE SET WAIT()?
            LoginFragment loginFragment = LoginFragment.newInstance((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE), getBaseContext(), this);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, loginFragment).addToBackStack(null).commit();
        }
    }


    @Override
    //method untuk langsung pindah ke login page
    public void changeBoardingPage(int page) {
        this.viewPager.setCurrentItem(page);
    }


    //method untuk pindah intent /activity ke mainActivity
    public void changeIntent(LoginResult loginResult){
        //intent change : SplashScreenActivity to MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //buat bundle object yg mau dikirim antar activity
                Bundle tokenBundle = new Bundle();
                tokenBundle.putString("USER_TOKEN", loginResult.getToken());
                tokenBundle.putString("USERNAME", loginResult.getUname());

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.putExtras(tokenBundle);
                startActivity(intent);
                finish();
            }
        }, 10);
    }


    /**
     * @deprecated as for now its fine to use :)
     * inner class untuk ganti on-boarding fragments (slides)
     * */
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
                    setupStatusBar(1);
                    OnBoarding1Fragment tab1 = OnBoarding1Fragment.newInstance(this.activity);
                    return tab1;
                case 1:
                    OnBoarding2Fragment tab2 = OnBoarding2Fragment.newInstance(this.activity);
                    return tab2;
                case 2:
                    LoginFragment tab3 = LoginFragment.newInstance((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE), getBaseContext(), this.activity);
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