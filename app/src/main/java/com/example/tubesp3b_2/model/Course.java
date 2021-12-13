package com.example.tubesp3b_2.model;

import java.util.ArrayList;
import java.util.Date;

public class Course {
    private String course_id, source, destination, vehicle, datetime;
    private int num_seats, fee;
    private ArrayList<Integer> seats;

    public Course(String course_id, String source, String destination, String datetime, String vehicle, int num_seats, ArrayList<Integer> seats, int fee){
        this.course_id = course_id;
        this.source = source;
        this.destination = destination;
        this.vehicle = vehicle;
        this.datetime = datetime;
        this.num_seats = num_seats;
        this.fee = fee;
        this.seats = seats;
    }

    public String getCourse_id(){
        return this.course_id;
    }

    public String getVehicle(){
        return this.vehicle;
    }

    public int getNum_seats(){
        return this.num_seats;
    }

    public ArrayList<Integer> getSeats(){
        return this.seats;
    }
}
