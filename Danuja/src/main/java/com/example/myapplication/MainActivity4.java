package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity4 extends AppCompatActivity {
    TextView cusId;
    EditText comment;
    Button sent;
    DatabaseReference ref1,ref2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        cusId = (TextView) findViewById(R.id.cusId);
        comment = (EditText) findViewById(R.id.comment);
        sent = findViewById(R.id.button);

        ref1 = FirebaseDatabase.getInstance().getReference().child("Customer");
        ref2 = FirebaseDatabase.getInstance().getReference().child("Feedback");
        
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String custID = snapshot.child("Cus_ID").getValue().toString();
                cusId.setText(custID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertFeedback();
            }
        });
    }

    private void insertFeedback() {
    }
}