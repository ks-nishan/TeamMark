package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {
    private FirebaseFirestore db;
    static String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        Email = intent.getStringExtra("EMAIL");
        EditText input1, input2, input3, input4;
        TextView input5;
        Button btn1,ButtonSearch,ViewAll;
        input1 = findViewById(R.id.P_Username);
        input2 = findViewById(R.id.P_Email);
        input3 = findViewById(R.id.P_Phone);
        input4 = findViewById(R.id.P_Password);
        input5 = findViewById(R.id.P_Username2);
        btn1 = findViewById(R.id.EditButton_P);

        ButtonSearch = findViewById(R.id.Pass_Booking);
        ViewAll = findViewById(R.id.View_ALL_Booking);


        db.collection("Customers").whereEqualTo("Email", Email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot snapshot : task.getResult()) {

                                ProfileUser model = new ProfileUser(snapshot.getString("UserName"), snapshot.getString("Email"), snapshot.getString("Password"), snapshot.getString("PhoneNumber"));


                                input4.setText(model.getPassword());
                                input3.setText(model.getPhoneNumber());
                                input2.setText(model.getEmail());
                                input1.setText(model.getUsername());
                                input5.setText(model.getUsername());
                                input4.setEnabled(false);

                            }
                        }
                    }
                });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Username = input1.getText().toString();
                String Email = input2.getText().toString();
                String Password = input4.getText().toString();
                String PhoneNumber = input3.getText().toString();


                Intent intent = new Intent(Profile.this, EditProfile.class);
                intent.putExtra("USERNAME",Username);
                intent.putExtra("EMAIL",Email);
                intent.putExtra("PS",Password);
                intent.putExtra("PHONE",PhoneNumber);
                startActivity(intent);
            }
        });

        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myinten = new Intent(Profile.this ,PastOrder.class);
                startActivity(myinten);
            }
        });

        ViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myinten = new Intent(Profile.this ,showAllPast.class);
                startActivity(myinten);
            }
        });



    }





}