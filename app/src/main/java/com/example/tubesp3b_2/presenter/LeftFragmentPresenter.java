package com.example.tubesp3b_2.presenter;

import com.example.tubesp3b_2.model.MenuNav;
import com.example.tubesp3b_2.view.ILeftFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeftFragmentPresenter {
    private List<MenuNav> menuNavs;
    private ILeftFragment ui;

    //constructor
    public LeftFragmentPresenter(ILeftFragment iNav){
        this.ui = iNav;
        this.menuNavs = new ArrayList<>();
    }

    //insert semua menu navigasi yg sdh tersedia dlm bentuk array
    public void loadData(MenuNav[] menuNavs){
        this.menuNavs.addAll(Arrays.asList(menuNavs));
        //update to lisview layout
        this.ui.updateToAdapter(this.menuNavs);
    }
}
