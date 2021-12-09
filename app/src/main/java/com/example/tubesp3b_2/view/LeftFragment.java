package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.databinding.LeftFragmentBinding;
import com.example.tubesp3b_2.model.MenuNav;
import com.example.tubesp3b_2.presenter.LeftFragmentPresenter;

import java.util.List;

public class LeftFragment extends Fragment implements ILeftFragment{
    private LeftFragmentBinding binding;
    private LeftFragmentAdapter adapter;
    private LeftFragmentPresenter presenter;

    //must-have empty constructor
    public LeftFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflating layout
        this.binding = LeftFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //menu" navigasi di drawer kiri
        MenuNav[] navs = {
                new MenuNav("Home"),
                new MenuNav("Book Ticket"),
                new MenuNav("History"),
                new MenuNav("Exit")
        };

        //inisialisasi presenter -> tempat melakukan operasi pada list sblm ditampilkan di list view (via Adapter)
        this.presenter = new LeftFragmentPresenter(this);

        //inisialisasi adapter -> tempat update layout listview & onclick listener per item list
        this.adapter = new LeftFragmentAdapter(getActivity(), this.getParentFragmentManager());

        //tambah semua menu yg sdh di list ke listviewnya
        this.presenter.loadData(navs);

        //lastly, jgn lupa set adapter buat listview yg ada di fragmentnya
        this.binding.lvNav.setAdapter(this.adapter);

        return view;
    }


    @Override
    public void updateToAdapter(List<MenuNav> updatedList) {
        this.adapter.update(updatedList);
    }
}
