package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView num, star, hig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = (TextView) findViewById(R.id.vehiNo);
        star = (TextView) findViewById(R.id.nostars);
        hig = (TextView) findViewById(R.id.ht);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Ratings");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String vehicle = snapshot.child("vehicle").getValue().toString();
                String rates = snapshot.child("rates").getValue().toString();
                String highest = snapshot.child("high").getValue().toString();
                num.setText(vehicle);
                star.setText(rates);
                hig.setText(highest);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void opensecond(View view) {
        Intent intent = new Intent(this, MainActivity2.class);

        startActivity(intent);

    }
}