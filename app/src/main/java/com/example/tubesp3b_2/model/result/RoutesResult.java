package com.example.tubesp3b_2.model.result;

import com.example.tubesp3b_2.model.Route;

public class RoutesResult {
    private Route[] payload;

    public RoutesResult(Route[] payload){
        this.payload = payload;
    }

    public Route[] getArrRoutes(){
        return this.payload;
    }
}
