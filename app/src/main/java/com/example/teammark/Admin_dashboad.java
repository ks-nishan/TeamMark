package com.example.teammark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teammark.parkingmanagement.ViewParkingAreaActivity;

public class Admin_dashboad extends AppCompatActivity implements View.OnClickListener{

    public CardView card1,card2,card3,card4,card5,card6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboad);

        card1 = (CardView)findViewById(R.id.cardRentACar);
        card2 = (CardView)findViewById(R.id.cardParkingSloat);
        card3 = (CardView)findViewById(R.id.cardResavation);
        card4 = (CardView)findViewById(R.id.cardFeedback);
        card5 = (CardView)findViewById(R.id.cardBuyACar);
        card6 = (CardView)findViewById(R.id.cardLogout);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.cardRentACar:
                i = new Intent(this, Admin_PostAD.class);
                startActivity(i);
                break;

            case R.id.cardParkingSloat:
                i = new Intent(this,ShowActivity.class);
                startActivity(i);
                break;

            case R.id.cardResavation:
                i = new Intent(this,PromoteAD2.class);
                startActivity(i);
                break;

            case R.id.cardFeedback:
                i = new Intent(this, ViewParkingAreaActivity.class);
                startActivity(i);
                break;

        }

    }
}