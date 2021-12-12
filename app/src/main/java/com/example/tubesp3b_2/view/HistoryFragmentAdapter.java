package com.example.tubesp3b_2.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b_2.databinding.HistoryFragmentContentBinding;
import com.example.tubesp3b_2.databinding.LeftFragmentMenuListItemBinding;
import com.example.tubesp3b_2.model.History;
import com.example.tubesp3b_2.model.MenuNav;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragmentAdapter extends BaseAdapter {
    private List<History> histories;
    private Activity activity;
    private FragmentManager fragmentManager;

    //constructor
    public HistoryFragmentAdapter(Activity activity, FragmentManager fragmentManager){
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.histories = new ArrayList<>();
    }


    //update to listview layout
    public void update(List<History> updatedHistories){
        this.histories = updatedHistories;
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return histories.size();
    }


    @Override
    public Object getItem(int i) {
        return histories.get(i);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //inflating to list item's layout
        HistoryFragmentContentBinding binding;
        if(view == null){
            binding = HistoryFragmentContentBinding.inflate(this.activity.getLayoutInflater());
            view = binding.getRoot();
            view.setTag(binding);
        }else{
            binding = (HistoryFragmentContentBinding) view.getTag();
        }

        //get current history to show
        History currentHistory = (History) this.getItem(i);
        binding.departingToArrivalCity.setText(currentHistory.getSource()+" to "+currentHistory.getDestination());
        binding.dateTime.setText(currentHistory.getCourse_datetime());
        binding.ticketsSeats.setText(currentHistory.getTicket_count());
        binding.vehicles.setText(currentHistory.getVehicle()+" car");

        return binding.getRoot();
    }
}
