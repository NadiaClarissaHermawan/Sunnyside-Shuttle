package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.databinding.HistoryFragmentBinding;
import com.example.tubesp3b_2.model.History;
import com.example.tubesp3b_2.presenter.HistoryFragmentPresenter;
import com.example.tubesp3b_2.view.interfaces.IHistoryFragment;

import java.util.List;

public class HistoryFragment extends Fragment implements IHistoryFragment {
    private HistoryFragmentBinding binding;
    private HistoryFragmentAdapter adapter;
    private HistoryFragmentPresenter presenter;

    //must-have empty constructor
    public HistoryFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflating layout
        this.binding = HistoryFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //inisialisasi presenter -> tempat melakukan operasi pada list sblm ditampilkan di list view (via Adapter)
        this.presenter = new HistoryFragmentPresenter(this);

        //inisialisasi adapter -> tempat update layout listview & onclick listener per item list
        this.adapter = new HistoryFragmentAdapter(getActivity(), this.getParentFragmentManager());

        //lastly, jgn lupa set adapter buat listview yg ada di fragmentnya
        this.binding.listviewHistory.setAdapter(this.adapter);

        return view;
    }


    @Override
    public void updateToAdapter(List<History> updatedHistories) {
        this.adapter.update(updatedHistories);
    }
}
