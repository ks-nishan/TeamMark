package com.teammark.parkingmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendAdmin(View view) {
        Intent intent = new Intent(this, ViewParkingAreaActivity.class);
        startActivity(intent);
    }

    public void sendCustomer(View view) {
        Intent intent = new Intent(this, FindParkingSlotActivity.class);
        startActivity(intent);
    }
}