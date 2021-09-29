package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        sent = findViewById(R.id.button4);

        ref1 = FirebaseDatabase.getInstance().getReference("Customer");
        ref2 = FirebaseDatabase.getInstance().getReference("Feedback");
        
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
                Intent intent = new Intent( MainActivity4.this,MainActivity5.class);
                startActivity(intent);
            }
        });
    }

    private void insertFeedback() {
        String custId = cusId.getText().toString();
        String data = comment.getText().toString();

        Feedback feedback = new Feedback(custId,data);

        ref2.child(custId).setValue(feedback);

        Toast.makeText(MainActivity4.this,"FeedBack Inserted", Toast.LENGTH_SHORT).show();

    }
}