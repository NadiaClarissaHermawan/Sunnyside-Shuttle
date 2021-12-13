package com.example.tubesp3b_2.model;

public class Route {
    private String source, destination;
    private int fee;

    public Route(String source, String destination, int fee){
        this.source = source;
        this.destination = destination;
        this.fee = fee;
    }

    public String getSource(){
        return this.source;
    }

    public String getDestination(){
        return this.destination;
    }

    public int getFee(){
        return this.fee;
    }
}
