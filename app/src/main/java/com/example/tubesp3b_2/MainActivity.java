package com.example.tubesp3b_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tubesp3b_2.databinding.ActivityMainBinding;
import com.example.tubesp3b_2.view.BookTicketFragment;
import com.example.tubesp3b_2.view.LandingPageFragment;
import com.example.tubesp3b_2.view.LoginFragment;
import com.example.tubesp3b_2.view.PaymentFragment;

public class MainActivity extends AppCompatActivity {
    //basic attrs
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private Fragment currentFragment;

    //fragment"
    private LandingPageFragment landingPageFragment;
    private BookTicketFragment bookTicketFragment;
    private PaymentFragment paymentFragment;

    //user attr needs
    private String user_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inflating layout
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        //ambil user's token yg dikirim dari SplashScreenActivity
        this.user_token = this.getIntent().getExtras().getString("USER_TOKEN");

        //setup drawer & toolbar
        this.setupDrawerToolbar();

        //inisialisasi atribut
        this.fragmentManager = this.getSupportFragmentManager();

        //inisiasi fragments
        this.landingPageFragment = new LandingPageFragment();
        this.bookTicketFragment = new BookTicketFragment();
        this.paymentFragment = new PaymentFragment();

        //set halaman pertama fragment = home page
        this.ft = this.fragmentManager.beginTransaction();
        this.ft.add(R.id.fragment_container, this.landingPageFragment).addToBackStack(null).commit();
        this.currentFragment = this.landingPageFragment;

        //listener untuk ganti page
        this.getSupportFragmentManager().setFragmentResultListener(
            "changePage", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    int page = result.getInt("page");
                    changePage(page);
                }
            }
        );
    }

    //method untuk setup toolbar & drawer
    public void setupDrawerToolbar(){
        //pasang drawer & toolbar
        this.drawer = this.binding.drawerLayout;
        this.toolbar = this.binding.toolbar;
        this.setSupportActionBar(toolbar);

        //pasang tombol hamburger (garis 3)
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, this.drawer, this.toolbar, 0, 0);
        abdt.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.orange));
        this.drawer.addDrawerListener(abdt);
        abdt.syncState();
    }

    //method untuk ganti page /fragment
    public void changePage (int page){
        this.ft = this.fragmentManager.beginTransaction().setCustomAnimations(
                //custom transition animation
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
        );

        //EXIT
        if(page == -1){
            this.closeApplication();

        //LANDING PAGE (HOME)
        }else if(page == 0){
            if(this.currentFragment != null){
                ft.hide(currentFragment);
            }

            if(this.landingPageFragment.isAdded()){
                ft.show(this.landingPageFragment);
            }else{
                ft.add(R.id.fragment_container, this.landingPageFragment);
            }

            this.currentFragment = this.landingPageFragment;

        //BOOK TICKET
        }else if(page == 1){
            if(this.currentFragment != null){
                ft.hide(currentFragment);
            }

            if(this.bookTicketFragment.isAdded()){
                ft.show(this.bookTicketFragment);
            }else{
                ft.add(R.id.fragment_container, this.bookTicketFragment);
            }

            this.currentFragment = this.bookTicketFragment;

        //SEAT
        // TODO: update page
        }else if(page == 2) {
            if (this.currentFragment != null) {
                ft.hide(currentFragment);
            }

            if (this.landingPageFragment.isAdded()) {
                ft.show(this.landingPageFragment);
            } else {
                ft.add(R.id.fragment_container, this.landingPageFragment);
            }

            this.currentFragment = this.landingPageFragment;

        //PAYMENT
        }else if(page == 3){
            if (this.currentFragment != null) {
                ft.hide(currentFragment);
            }

            if (this.paymentFragment.isAdded()) {
                ft.show(this.paymentFragment);
            } else {
                ft.add(R.id.fragment_container, this.paymentFragment);
            }

            this.currentFragment = this.paymentFragment;

        //HISTORY
        //TODO: Update page
        }else if(page == 4){
            if (this.currentFragment != null) {
                ft.hide(currentFragment);
            }

            if (this.paymentFragment.isAdded()) {
                ft.show(this.paymentFragment);
            } else {
                ft.add(R.id.fragment_container, this.paymentFragment);
            }

            this.currentFragment = this.paymentFragment;
        }
    }

    //tutup aplikasi
    public void closeApplication(){
        this.moveTaskToBack(true);
        this.finish();
    }
}