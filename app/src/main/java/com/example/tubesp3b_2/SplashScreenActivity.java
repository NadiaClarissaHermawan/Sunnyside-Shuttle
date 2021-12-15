package com.example.tubesp3b_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.tubesp3b_2.databinding.ActivitySplashScreenBinding;
import com.example.tubesp3b_2.model.SharedPref;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.model.room_database.AppDataBase;
import com.example.tubesp3b_2.presenter.LoginCheckerThread;
import com.example.tubesp3b_2.view.interfaces.IBoardingScreen;
import com.example.tubesp3b_2.view.LoginFragment;
import com.example.tubesp3b_2.view.OnBoarding1Fragment;
import com.example.tubesp3b_2.view.OnBoarding2Fragment;

import org.parceler.Parcels;

public class SplashScreenActivity extends AppCompatActivity implements IBoardingScreen {
    private ActivitySplashScreenBinding binding;
    private SharedPref sp;
    private AppDataBase db;
    private LoginCheckerThread checkerLogin;

    //on-boarding page needs
    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    //change intent needs
    private int delay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inflating layout
        super.onCreate(savedInstanceState);
        this.binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        //attributes initialization
        this.sp = new SharedPref(getBaseContext());
        this.db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "pppb_tubes2_database").build();
        this.checkerLogin =  new LoginCheckerThread(this.db, this);
        this.delay = 4000;

        //check if logout
        this.checkLogout();

        //check if theres still user's info on database
        this.checkUser();

        //set splash screen's animations
        this.setSplashAnimation();

        //setup status bar
        this.setupStatusBar(0);
    }


    //set splash screen animation
    public void setSplashAnimation(){
        this.binding.splashBg.animate().translationY(-2400).setDuration(1000).setStartDelay(4000);
        this.binding.logo.animate().translationY(2050).setDuration(1000).setStartDelay(4000);
        this.binding.appName.animate().translationY(2050).setDuration(1000).setStartDelay(4000);
        this.binding.lottieShuttle.animate().translationY(2050).setDuration(1000).setStartDelay(4000);
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


    //check if user choose to logout from MainActivity
    public void checkLogout(){
        if(this.getIntent().hasExtra("user")){
            User user = Parcels.unwrap(this.getIntent().getExtras().getParcelable("user"));
            new LoginCheckerThread(this.db, this).removeUser(user.getUsername(), user.getToken());
        }
    }


    //check if user info is still on database
    public void checkUser(){
       this.checkerLogin.startThread();
    }


    //getter checker response
    public void getResultCheckUser(String uname, String token){
        //show on boarding screen
        if(uname == "" && token == ""){
            this.showBoardingScreen();
        //move intent
        }else{
            this.changeIntent(uname, token);
        }
    }


    //show onboarding screen or nah
    public void showBoardingScreen(){
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
    }


    @Override
    //method untuk langsung pindah ke login page
    public void changeBoardingPage(int page) {
        this.viewPager.setCurrentItem(page);
    }


    //method untuk pindah intent /activity ke mainActivity
    public void changeIntent(String uname, String token){
        if(this.checkerLogin.getLogin_indicator() == 0){
            new LoginCheckerThread(this.db, this).addUser(uname, token);
            this.delay = 10;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //intent change : SplashScreenActivity to MainActivity
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle tokenBundle = new Bundle();
                        tokenBundle.putParcelable("user", Parcels.wrap(new User(0, uname, token)));

                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        intent.putExtras(tokenBundle);
                        startActivity(intent);
                        finish();
                    }
                }, delay);
            }
        });
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
                    LoginFragment tab3 = LoginFragment.newInstance((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE), getBaseContext(), this.activity, db);
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