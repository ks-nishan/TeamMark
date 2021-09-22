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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Payment extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        db = FirebaseFirestore.getInstance();
        Intent myIntent = getIntent();

        String Model = myIntent.getStringExtra("MODEL");
        String Date1 = myIntent.getStringExtra("DATE1");
        String Date2 = myIntent.getStringExtra("DATE2");
        String total = myIntent.getStringExtra("TOTAL");
        String location = myIntent.getStringExtra("LOCATION");
        String Email = myIntent.getStringExtra("EMAIL");

        EditText in1,in2,in3,in4,in5;
        TextView T1,T2;
        Button btn1;
        btn1 = findViewById(R.id.PAY_NOW_BUTTON);
        in1 = findViewById(R.id.Card_Number);
        in2 = findViewById(R.id.Holder_Name);
        in3 = findViewById(R.id.Card_Number_MM);
        in4 = findViewById(R.id.Card_Number_YY);
        in5 = findViewById(R.id.Card_Number_CVC);
        T1 = findViewById(R.id.Booking_ID);
        T2 = findViewById(R.id.Card_Number_TOT);
        Random random = new Random();

        T1.setText("TM"+random.nextInt(1000));
        T2.setText(total.toString());
        String BookingID = T1.getText().toString();




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BookingID.isEmpty()&& !Model.isEmpty() && !Date1.isEmpty() && !Date2.isEmpty() && !Email.isEmpty() && !location.isEmpty() && !total.isEmpty()  && !in1.getText().toString().isEmpty() && !in2.getText().toString().isEmpty()  && !in3.getText().toString().isEmpty()  && !in4.getText().toString().isEmpty() && !in5.getText().toString().isEmpty()   ){

                    SaveBooking(BookingID,Model,Date1,Date2,Email,location,total,in1.getText().toString(),in2.getText().toString(),in3.getText().toString(),in4.getText().toString(),in5.getText().toString());
                    SaveOnGoing(BookingID,Date1,Date2,Model,location,Email);
                    Intent intent = new Intent(Payment.this, OnGoing.class);
                    intent.putExtra("EMAIL",Email);
                    startActivity(intent);


                }
                else {

                    Toast.makeText(Payment.this,"cannot be empty",Toast.LENGTH_LONG).show();
                }



            }
        });







    }



    private void SaveBooking(String bookingID, String model, String date1, String date2, String email, String location, String total, String toString, String toString1, String toString2, String toString3, String toString4) {


        if (!bookingID.isEmpty()&& !model.isEmpty() && !date1.isEmpty() && !date2.isEmpty() && !email.isEmpty() && !location.isEmpty() && !total.isEmpty()  && !toString.isEmpty() && !toString1.isEmpty()  && !toString2.isEmpty()  && !toString3.isEmpty() && !toString4.isEmpty()   ) {
            HashMap<String,Object> map = new HashMap<>();

            map.put("BookingID",bookingID);
            map.put("Model",model);
            map.put("PickupDate",date1);
            map.put("ReturnDate",date2);
            map.put("Email",email);
            map.put("Location",location);
            map.put("Total",total);
            map.put("Card Number",toString);
            map.put("Card Holder",toString1);
            map.put("MM",toString2);
            map.put("YY",toString3);
            map.put("CVC",toString4);






            db.collection("Vehicle_Booking").document(bookingID).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Payment.this,"Booking confirmed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                    Toast.makeText(Payment.this,"Data not saved",Toast.LENGTH_SHORT).show();
                }
            });



        }
        else {
            Toast.makeText(Payment.this,"cannot be empty",Toast.LENGTH_LONG).show();
        }




    }








    private void SaveOnGoing(String bookingID, String date1,String date2,String model, String location,String Email) {

        if (!bookingID.isEmpty() && !model.isEmpty() && !date1.isEmpty() && !location.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();

            map.put("BookingID", bookingID);
            map.put("Model", model);
            map.put("PickupDate", date1);
            map.put("ReturnDate", date2);
            map.put("Location", location);
            map.put("Email", Email);


            db.collection("On_Going").document(Email).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {


                }
            });


        } else {

        }


    }
}


