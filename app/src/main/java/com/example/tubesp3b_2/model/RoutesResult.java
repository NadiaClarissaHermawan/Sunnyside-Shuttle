package com.example.tubesp3b_2.model;

public class RoutesResult {
    private Routes[] payload;

    public RoutesResult(Routes[] payload){
        this.payload = payload;
    }

    public Routes[] getArrRoutes(){
        return this.payload;
    }
}
