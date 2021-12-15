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
import android.os.Handler;
import android.view.View;

import com.example.tubesp3b_2.databinding.ActivityMainBinding;
import com.example.tubesp3b_2.model.result.CoursesResult;
import com.example.tubesp3b_2.model.result.HistoriesResult;
import com.example.tubesp3b_2.model.result.RoutesResult;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.model.room_database.AppDataBase;
import com.example.tubesp3b_2.view.BookTicketFragment;
import com.example.tubesp3b_2.view.HistoryFragment;
import com.example.tubesp3b_2.view.LandingPageFragment;
import com.example.tubesp3b_2.view.PaymentFragment;
import com.example.tubesp3b_2.view.SeatFragment;

import org.parceler.Parcels;

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
    private SeatFragment seatFragment;
    private PaymentFragment paymentFragment;
    private HistoryFragment historyFragment;

    //user attr needs
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inflating layout
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        //take user's info
        this.user = Parcels.unwrap(this.getIntent().getExtras().getParcelable("user"));

        //setup drawer & toolbar
        this.setupDrawerToolbar();

        //inisialisasi atribut
        this.fragmentManager = this.getSupportFragmentManager();

        //inisiasi fragments
        this.landingPageFragment = LandingPageFragment.newInstance(this.user, this.getSupportFragmentManager());
        this.bookTicketFragment = BookTicketFragment.newInstance(this.user, this.getSupportFragmentManager(), this);
        this.seatFragment = SeatFragment.newInstance(this.user, this.getSupportFragmentManager(), this);
        this.paymentFragment = PaymentFragment.newInstance(this.user, this.getSupportFragmentManager(), this);
        this.historyFragment = HistoryFragment.newInstance(this.user, this);

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


    //method untuk terima & salurin response route
    public void giveRoutesResponse(RoutesResult res){
        this.bookTicketFragment.addRoutesArray(res);
    }


    //method untuk terima & salurin response course
    public void giveCoursesResponse(CoursesResult res){
        this.seatFragment.getCourseInfo(res.getCoursesResult());
    }


    //method untuk terima & salurin response history
    public void giveHistoryResponse(HistoriesResult res){
        this.historyFragment.updateToPresenter(res.getArrHistories());
    }


    //method untuk terima & salurin response payment confirmed & update history
    public void givePostOrderResponse(){
        this.paymentFragment.paymentSucceed();
        this.historyFragment.requestHistoryTask();
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
        }else if(page == 2) {
            if (this.currentFragment != null) {
                ft.hide(currentFragment);
            }
            if (this.seatFragment.isAdded()) {
                ft.show(this.seatFragment);
            } else {
                ft.add(R.id.fragment_container, this.seatFragment);
            }
            this.currentFragment = this.seatFragment;

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
        }else if(page == 4){
            if (this.currentFragment != null) {
                ft.hide(currentFragment);
            }
            if (this.historyFragment.isAdded()) {
                ft.show(this.historyFragment);
            } else {
                ft.add(R.id.fragment_container, this.historyFragment);
            }
            this.currentFragment = this.historyFragment;

        //SIGN OUT
        }else{
            this.changeIntent();
        }

        //closing left drawer
        this.binding.drawerLayout.closeDrawers();

        //commit change page
        ft.commit();
    }


    @Override
    //kalau tombol back ditekan
    public void onBackPressed(){
        if(this.currentFragment == this.bookTicketFragment || this.currentFragment == this.historyFragment){
            this.changePage(0);
        }else if(this.currentFragment == this.seatFragment){
            this.seatFragment.resetVehicleType();
            this.changePage(1);
        }else if(this.currentFragment == this.paymentFragment){
            this.changePage(2);
        }else if(this.currentFragment == this.landingPageFragment){
            this.changePage(-1);
        }
    }


    //change intent MainActivity -> SplashActivity
    public void changeIntent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle tokenBundle = new Bundle();
                tokenBundle.putParcelable("user", Parcels.wrap(user));

                Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                intent.putExtras(tokenBundle);
                startActivity(intent);
                finish();
            }
        }, 0);
    }


    //tutup aplikasi
    public void closeApplication(){
        this.moveTaskToBack(true);
        this.finish();
    }
}