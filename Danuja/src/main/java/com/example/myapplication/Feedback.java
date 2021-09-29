package com.example.myapplication;

public class Feedback {
    public String getCusID() {
        return custID;
    }

    public String getComment() {
        return data;
    }

    String custID;
    String data;

    public Feedback(String custID, String data){
        this.custID = custID;
        this.data = data;

    }

}
