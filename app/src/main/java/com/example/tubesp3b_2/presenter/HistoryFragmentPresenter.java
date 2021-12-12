package com.example.tubesp3b_2.presenter;

import com.example.tubesp3b_2.model.History;
import com.example.tubesp3b_2.view.interfaces.IHistoryFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryFragmentPresenter {
    private List<History> histories;
    private IHistoryFragment ui;

    //constructor
    public HistoryFragmentPresenter(IHistoryFragment iHistory){
        this.ui = iHistory;
        this.histories = new ArrayList<>();
    }


    //insert semua history yg sdh diambil dari API
    //TODO: LENGKAPIN pengambilan data
    public void loadData(History[] histories){
        this.histories.addAll(Arrays.asList(histories));
        //update to lisview layout
        this.ui.updateToAdapter(this.histories);
    }
}
