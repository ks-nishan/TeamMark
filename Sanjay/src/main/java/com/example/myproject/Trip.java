package com.example.myproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Trip extends AppCompatActivity {
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        db = FirebaseFirestore.getInstance();
        Intent myIntent = getIntent();
        String Email = myIntent.getStringExtra("EMAIL");
        TextView T1, T2,  T3 , T4;
        Button Btn1, btn2 ;

        T1 = findViewById( R.id.Trip_Location);
        T2 = findViewById( R.id.Trip_duration);
        T3 = findViewById( R.id.Trip_Username);
        T4 = findViewById( R.id.Trip_Model);

        Btn1 = findViewById(R.id.Trip_Park_button);
        btn2 = findViewById(R.id.Trip_End_button);



        db.collection("On_Going").whereEqualTo("Email", Email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot snapshot : task.getResult()) {

                                T1.setText(snapshot.getString("Location"));
                                String pick = snapshot.getString("PickupDate");
                                String Return = snapshot.getString("ReturnDate");
                                try {
                                   int d = durationCalc(pick,Return);
                                  String duration = String.valueOf(d) ;
                                  T2.setText(duration + " Days");

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                T3.setText(snapshot.getString("BookingID"));
                                T4.setText(snapshot.getString("Model"));

                            }
                        }
                    }


                });



        Btn1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



    }
});

btn2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});









    }

    private int durationCalc(String pick, String aReturn) throws ParseException {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d1 = sdf.parse(pick);
        Date d2 = sdf.parse(aReturn);

        long Time = d2.getTime() - d1.getTime();
        long Days = (Time
                / (1000 * 60 * 60 * 24))
                % 365;
        int Day = (int)Days;
        return  Day;
    }




}