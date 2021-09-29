package com.example.myapplication;

public class ParkReview {
    public String getPreview() {
        return preview;
    }

    public String getData() {
        return data;
    }

    String preview;
    String data;

    public ParkReview(String preview, String data){
        this.preview = preview;
        this.data = data;

    }
}
