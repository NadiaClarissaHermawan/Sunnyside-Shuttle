package com.example.tubesp3b_2.model;

public class TicketOrder {
    private String source, destination, vehicle, date;
    private int hour;

    public TicketOrder(String source, String destination, String vehicle, String date, int hour){
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.hour = hour;
    }

    public void setVehicle(int type){
        if(type == 0){
            this.vehicle = "small";
        }else{
            this.vehicle = "Large";
        }
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

    public int getHour(){
        return this.hour;
    }
}
