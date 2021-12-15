package com.example.tubesp3b_2.model.result;

import com.example.tubesp3b_2.model.Course;

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
