package com.example.tubesp3b_2.view.interfaces;

import com.example.tubesp3b_2.model.History;
import com.example.tubesp3b_2.model.MenuNav;

import java.util.List;

public interface IHistoryFragment {
    public void updateToAdapter(List<History> updatedHistories);
}
