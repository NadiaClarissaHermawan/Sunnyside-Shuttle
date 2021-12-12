package com.example.tubesp3b_2.model;

public class TicketOrder {
    private String source, destination, vehicle, date, hour;
    private int seat;

    public TicketOrder(String source, String destination, String vehicle, String date, String hour){
        this.source = source;
        this.destination = destination;
        this.vehicle = vehicle;
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

    public void setSeat(int number){
        this.seat = number;
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

    public int getSeat(){
        return this.seat;
    }
}
