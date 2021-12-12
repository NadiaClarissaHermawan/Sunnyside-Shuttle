package com.example.tubesp3b_2.model;


import java.util.ArrayList;
import java.util.Date;

public class History {
    private String order_id, course_id, source, destination, vehicle, order_datetime, course_datetime;
    private ArrayList<Integer> seats;
    private int fee, ticket_count;

    public History(String order_id, String course_id, String source, String destination, String vehicle, String order_datetime, String course_datetime, ArrayList<Integer> seats, int fee){
        this.order_id = order_id;
        this.course_id = course_id;
        this.source = source;
        this.destination = destination;
        this.vehicle = vehicle;
        this.order_datetime = order_datetime;
        this.course_datetime = course_datetime;
        this.seats = seats;
        this.fee = fee;

        this.ticket_count = seats.size();
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

    public String getOrder_datetime(){
        return this.order_datetime;
    }

    public String getCourse_datetime(){
        return this.course_datetime;
    }

    public ArrayList<Integer> getSeats(){
        return this.seats;
    }

    public int getFee(){
        return this.fee;
    }

    public String getTicket_count(){
        String res = this.ticket_count+"x tickets (";
        for(int i = 0; i<this.ticket_count; i++){
            if(i == this.ticket_count-1){
                res = res + this.seats.get(i) + ")";
            }else {
                res = res + this.seats.get(i) + ",";
            }
        }
        return res;
    }
}
