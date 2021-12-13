package com.example.tubesp3b_2.model;

public class TicketOrder {
    private String source, destination, vehicle, date, hour;

    public TicketOrder(String source, String destination, String vehicle, String date, String hour){
        this.source = source;
        this.destination = destination;
        this.vehicle = vehicle;
        this.date = date;
        this.hour = hour;
    }

    public void setVehicle(String type){
        this.vehicle = type;
    }

    public String getSource(){
        return this.source;
    }

    public String getDestination(){
        return this.destination;
    }

    public String getVehicle(){
        return this.vehicle;
    }

    public String getDate(){
        return this.date;
    }

    public String getHour(){
        return this.hour;
    }
}
