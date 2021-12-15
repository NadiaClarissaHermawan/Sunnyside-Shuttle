package com.example.tubesp3b_2.model.result;

import com.example.tubesp3b_2.model.History;

public class HistoriesResult {
    private History[] payload;

    public HistoriesResult(History[] payload){
        this.payload = payload;
    }

    public History[] getArrHistories(){
        return this.payload;
    }
}
