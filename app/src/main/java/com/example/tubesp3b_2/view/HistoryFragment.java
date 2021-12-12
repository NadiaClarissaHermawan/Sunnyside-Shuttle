package com.example.tubesp3b_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.databinding.HistoryFragmentBinding;
import com.example.tubesp3b_2.model.HistoriesResult;
import com.example.tubesp3b_2.model.History;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.presenter.GetCoursesTask;
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
        new GetHistoryTask(this.getContext(), this.activity, this.user.getToken()).executeHistory();

        //TODO:TESTER JANGAN LUPA HAPUS
        ArrayList<Integer> sit = new ArrayList<>();
        sit.add(3);
        sit.add(4);
        History[] his = {new History("1",  "2",  "Bandung", "Jakarta", "Small", "10-10-2021", "26-11-2021", sit, 50000),
                new History("1",  "2",  "Bandung", "Jakarta", "Small", "10-10-2021", "26-11-2021", sit, 50000)};
        this.presenter.loadData(his);

        return view;
    }


    //TODO: sebenernya gausah pake presenter jg gpp krn gaada operasi ke listnya,
    // cmn just in case nambah fitur apus history jgn dihapus dl
    public void updateToPresenter(History[] retrievedHistories){
        this.presenter.loadData(retrievedHistories);
    }


    @Override
    //update to listview layout
    public void updateToAdapter(List<History> updatedHistories) {
        this.adapter.update(updatedHistories);
    }
}
