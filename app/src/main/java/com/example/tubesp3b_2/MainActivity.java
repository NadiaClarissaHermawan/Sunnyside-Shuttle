package com.example.tubesp3b_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tubesp3b_2.databinding.ActivityMainBinding;
import com.example.tubesp3b_2.view.LoginFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private Fragment currentFragment;

    //fragment"
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inflating layout
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        //pasang drawer
        this.drawer = this.binding.drawerLayout;

        //inisialisasi atribut
        this.fragmentManager = this.getSupportFragmentManager();

        //inisiasi fragments
        this.loginFragment = new LoginFragment();

        //set halaman pertama fragment = home page
        this.ft = this.fragmentManager.beginTransaction();
        this.ft.add(R.id.fragment_container, this.loginFragment).addToBackStack(null).commit();
        this.currentFragment = this.loginFragment;

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
        //LOGIN PAGE
        }else if(page == 0){
            if(this.currentFragment != null){
                ft.hide(currentFragment);
            }

            if(this.loginFragment.isAdded()){
                ft.show(this.loginFragment);
            }else{
                ft.add(R.id.fragment_container, this.loginFragment);
            }

            this.currentFragment = this.loginFragment;
        }//add next fragments
    }

    //tutup aplikasi
    public void closeApplication(){
        this.moveTaskToBack(true);
        this.finish();
    }
}