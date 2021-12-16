package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.databinding.HistoryFragmentBinding;
import com.example.tubesp3b_2.model.History;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.presenter.GetHistoryTask;
import com.example.tubesp3b_2.presenter.HistoryFragmentPresenter;
import com.example.tubesp3b_2.view.interfaces.IHistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements IHistoryFragment {
    private HistoryFragmentBinding binding;
    private HistoryFragmentAdapter adapter;
    private HistoryFragmentPresenter presenter;
    private MainActivity activity;
    private User user;
    private int lastIdx;
    private boolean indicatorUpdate;

    //must-have empty constructor
    public HistoryFragment(){}


    //singleton
    public static HistoryFragment newInstance(User user, MainActivity activity){
        HistoryFragment fragment = new HistoryFragment();
        fragment.user = user;
        fragment.activity = activity;
        fragment.lastIdx = 1;
        fragment.indicatorUpdate = false;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflating layout
        this.binding = HistoryFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //inisialisasi presenter
        this.presenter = new HistoryFragmentPresenter(this);

        //inisialisasi adapter
        this.adapter = new HistoryFragmentAdapter(getActivity(), this.getParentFragmentManager());

        //lastly, jgn lupa set adapter buat listview yg ada di fragmentnya
        this.binding.listviewHistory.setAdapter(this.adapter);

        //get order list dari API
        this.updateToPresenter(null);

        return view;
    }


    //get chunks of history
    public void updateToPresenter(History[] retrievedHistories){
        //first time retrieving the history
        if(retrievedHistories==null && lastIdx == 1) {
            new GetHistoryTask(this.getContext(), this.activity, this.user.getToken()).executeHistory(0, 10);

        //updating new added history
        }else if(retrievedHistories== null && lastIdx != 1){
            this.indicatorUpdate = true;
            new GetHistoryTask(this.getContext(), this.activity, this.user.getToken()).executeHistory(0, 1);
            
        //get first chunk & other chunks
        }else if(retrievedHistories != null && retrievedHistories.length == 10){
            this.lastIdx += 10;
            this.presenter.addHistory(retrievedHistories);
            new GetHistoryTask(this.getContext(), this.activity, this.user.getToken()).executeHistory(this.lastIdx, 10);

        //last chunks
        }else if(retrievedHistories != null && retrievedHistories.length < 10){
            if(!this.indicatorUpdate){
                this.lastIdx += retrievedHistories.length;
                this.presenter.addHistory(retrievedHistories);
            }else{
                this.indicatorUpdate = false;
                this.presenter.updateNewHistory(retrievedHistories);
            }
            this.presenter.sendAllToFrag();
        }
    }


    @Override
    //update to listview layout
    public void updateToAdapter(List<History> updatedHistories) {
        this.adapter.update(updatedHistories);
    }
}
