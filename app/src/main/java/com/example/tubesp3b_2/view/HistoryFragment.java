package com.example.tubesp3b_2.view;

import android.os.Bundle;
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

    //must-have empty constructor
    public HistoryFragment(){}


    //singleton
    public static HistoryFragment newInstance(User user, MainActivity activity){
        HistoryFragment fragment = new HistoryFragment();
        fragment.user = user;
        fragment.activity = activity;

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
        this.requestHistoryTask();

        return view;
    }


    //request payment history
    public void requestHistoryTask(){
        new GetHistoryTask(this.getContext(), this.activity, this.user.getToken()).executeHistory();
    }


    //request to renew data at presenter
    public void updateToPresenter(History[] retrievedHistories){
        this.presenter.loadData(retrievedHistories);
    }


    @Override
    //update to listview layout
    public void updateToAdapter(List<History> updatedHistories) {
        this.adapter.update(updatedHistories);
    }
}
