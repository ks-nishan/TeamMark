package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {
    EditText rev1;
    Button submit;
    RatingBar ratingBar2;

    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_review);

        rev1= findViewById(R.id.Preview);
        ratingBar2 = findViewById(R.id.ratingBar2);
        submit=findViewById(R.id.button);

        ref = FirebaseDatabase.getInstance().getReference().child("Parking_Reviews");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertParkingReview();
                Intent intent = new Intent( MainActivity3.this,MainActivity4.class);
                startActivity(intent);
            }
        });
    }

    private void insertParkingReview() {

        String preview = rev1.getText().toString();
        String data = String.valueOf(ratingBar2.getRating());

        ParkReview parkReview = new ParkReview(preview,data);
        ref.push().setValue(parkReview);

        Toast.makeText(MainActivity3.this,"Parking Review Inserted", Toast.LENGTH_SHORT).show();
    }


}
