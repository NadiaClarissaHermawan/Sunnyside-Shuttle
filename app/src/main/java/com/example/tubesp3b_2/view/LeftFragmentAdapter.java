package com.example.tubesp3b_2.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b_2.databinding.ActivityMainBinding;
import com.example.tubesp3b_2.databinding.LeftFragmentMenuListItemBinding;
import com.example.tubesp3b_2.model.MenuNav;
import com.example.tubesp3b_2.presenter.LeftFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

public class LeftFragmentAdapter extends BaseAdapter {
    private List<MenuNav> menuNavs;
    private Activity activity;
    private FragmentManager fragmentManager;

    //constructor
    public LeftFragmentAdapter(Activity activity, FragmentManager fragmentManager){
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.menuNavs = new ArrayList<>();
    }

    //update to listview layout
    public void update(List<MenuNav> updatedMenuNavs){
        this.menuNavs = updatedMenuNavs;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return menuNavs.size();
    }

    @Override
    public Object getItem(int i) {
        return menuNavs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //inflating to list item's layout
        LeftFragmentMenuListItemBinding binding;
        if(view == null){
            binding = LeftFragmentMenuListItemBinding.inflate(this.activity.getLayoutInflater());
            view = binding.getRoot();
            view.setTag(binding);
        }else{
            binding = (LeftFragmentMenuListItemBinding) view.getTag();
        }

        //get current nav menu to show
        MenuNav currentNavMenu = (MenuNav) this.getItem(i);
        binding.tvNavItem.setText(currentNavMenu.getName());

        //set listener on current nav menu
        new ViewHolder(this.fragmentManager, currentNavMenu, binding);

        return binding.getRoot();
    }

    private class ViewHolder implements View.OnClickListener{
        private FragmentManager fragmentManager;
        private MenuNav currentNavMenu;
        private LeftFragmentMenuListItemBinding binding;

        public ViewHolder(FragmentManager fragmentManager, MenuNav currentNavMenu, LeftFragmentMenuListItemBinding binding){
            this.fragmentManager = fragmentManager;
            this.currentNavMenu = currentNavMenu;
            this.binding = binding;

            this.setClickListener();
        }

        //set click listener tdk boleh langsung di constructor ViewHolder
        public void setClickListener(){
            this.binding.tvNavItem.setOnClickListener(this::onClick);
        }

        @Override
        //move page
        public void onClick(View view) {
            Bundle nextPage = new Bundle();

            if(this.binding.tvNavItem.getText().toString().equals("Exit")){
                nextPage.putInt("page", -1);
            }else if(this.binding.tvNavItem.getText().toString().equals("Home")){
                nextPage.putInt("page", 0);
            }else if(this.binding.tvNavItem.getText().toString().equals("Book Ticket")){
                nextPage.putInt("page", 1);
            }else if(this.binding.tvNavItem.getText().toString().equals("History")){
                nextPage.putInt("page", 4);
            }

            //kirim ke MainActivity.java
            this.fragmentManager.setFragmentResult("changePage", nextPage);
        }
    }
}
