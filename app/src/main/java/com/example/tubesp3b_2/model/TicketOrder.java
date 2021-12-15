package com.example.tubesp3b_2.model;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class TicketOrder {
    String source, destination, vehicle, date, hour, course_id;
    ArrayList<Integer> seats;
    int fee;

    public TicketOrder(){}

    public TicketOrder(String source, String destination, String vehicle, String date, String hour){
        this.source = source;
        this.destination = destination;
        this.vehicle = vehicle;
        this.date = date;
        this.hour = hour;
        this.seats = new ArrayList<>();
    }

    public void setCourse_id(String course_id){
        this.course_id = course_id;
    }

    public void setFee(int fee){
        this.fee = fee;
    }

    public void setSeats(ArrayList<Integer> seats){
        this.seats = seats;
    }

    public void setVehicle(String type){
        this.vehicle = type;
    }

    public String getCourse_id(){
        return this.course_id;
    }

    public int getFee(){
        return this.fee;
    }

    public ArrayList<Integer> getSeats(){
        return this.seats;
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
