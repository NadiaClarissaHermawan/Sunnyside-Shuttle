package com.example.tubesp3b_2.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.model.History;
import com.example.tubesp3b_2.view.interfaces.IHistoryFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HistoryFragmentPresenter {
    private List<History> histories;
    private IHistoryFragment ui;

    //constructor
    public HistoryFragmentPresenter(IHistoryFragment iHistory){
        this.ui = iHistory;
        this.histories = new ArrayList<>();
    }


    //add more history chunks
    public void addHistory(History[] historiesChunk){
        if(historiesChunk != null && historiesChunk.length > 0){
            this.histories.addAll(Arrays.asList(historiesChunk));
        }
    }


    //update newest history (add front)
    public void updateNewHistory(History[] newHistories){
        ArrayList<History> updateItem = new ArrayList<>();
        updateItem.addAll(Arrays.asList(newHistories));
        updateItem.addAll(this.histories);

        this.histories = updateItem;
    }


    //send complete history to historyFragment
    public void sendAllToFrag(){
        this.ui.updateToAdapter(this.histories);
    }
}
