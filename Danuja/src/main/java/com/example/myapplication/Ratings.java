package com.example.myapplication;

public class Ratings {

    public String getReview() {
        return review;
    }

    public String getData() {
        return data;
    }



    String review;
    String data;

    public Ratings(String review, String data){
        this.review = review;
        this.data = data;


    }
}
