package com.example.tubesp3b_2.model;

public class Seat {
    private int seat_id, status, top, bottom, right, left;

    public Seat(int seat_id, int status, int top, int bottom, int right, int left){
        this.seat_id = seat_id;
        this.status = status;
        this.top = top;
        this.bottom = bottom;
        this.right = right;
        this.left = left;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public void setTop(int top){
        this.top = top;
    }

    public void setBottom(int bottom){
        this.bottom = Seat.this.bottom;
    }

    public void setLeft(int left){
        this.left = left;
    }

    public void setRight(int right){
        this.right = Seat.this.right;
    }

    public int getStatus(){
        return this.status;
    }

    public int getTop(){
        return this.top;
    }

    public int getBottom(){
        return this.bottom;
    }

    public int getLeft(){
        return this.left;
    }

    public int getRight(){
        return this.right;
    }
}
