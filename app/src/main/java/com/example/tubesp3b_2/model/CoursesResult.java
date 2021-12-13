package com.example.tubesp3b_2.model;

import java.util.ArrayList;

public class CoursesResult {
    private ArrayList<Course> payload;

    public CoursesResult(ArrayList<Course> payload){
        this.payload = payload;
    }

    public ArrayList<Course>  getCoursesResult(){
        return  this.payload;
    }
}
