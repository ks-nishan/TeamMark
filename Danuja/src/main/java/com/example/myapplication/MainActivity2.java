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

public class MainActivity2 extends AppCompatActivity {
    EditText rev;
    Button submit, next;
    RatingBar ratingBar;

    DatabaseReference reviewref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_review);

        rev = findViewById(R.id.Preview);
        submit = findViewById(R.id.button);
        ratingBar = findViewById(R.id.ratingBar);
        next=findViewById(R.id.go);

        reviewref = FirebaseDatabase.getInstance().getReference().child("Vehicle_Reviews");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRatings();
                Intent intent = new Intent( MainActivity2.this,MainActivity4.class);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                insertRatings();
                Intent intent = new Intent( MainActivity2.this,MainActivity3.class);
                startActivity(intent);
            }
        });
    }

    private void insertRatings() {
        String review = rev.getText().toString();
        String data = String.valueOf(ratingBar.getRating());

        Ratings ratings = new Ratings(review,data);

        reviewref.push().setValue(ratings);

        Toast.makeText(MainActivity2.this,"Vehicle Review Inserted", Toast.LENGTH_SHORT).show();
    }

}