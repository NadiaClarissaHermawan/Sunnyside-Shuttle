package com.example.tubesp3b_2.model.request;

import java.util.ArrayList;

public class ConfirmedOrderInput {
    String course_id;
    String seats;

    public ConfirmedOrderInput(String course_id, String seats){
        this.course_id = course_id;
        this.seats = seats;
    }
}
