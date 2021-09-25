package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OnGoing extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going);
        db = FirebaseFirestore.getInstance();
        Intent myIntent = getIntent();
        String Email = myIntent.getStringExtra("EMAIL");
        TextView T1;
        TextView E1, E2, E3;
        Button btn1;

        T1 = findViewById(R.id.OnGoing_Date);
        E1 = findViewById(R.id.OnGoing_BookingId);
        E2 = findViewById(R.id.OnGoing_Pickup);
        E3 = findViewById(R.id.OnGoing_Model);
        btn1 = findViewById(R.id.OnGoing_Button);


        db.collection("On_Going").whereEqualTo("Email", Email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot snapshot : task.getResult()) {

                                T1.setText(snapshot.getString("Location"));
                                E1.setText(snapshot.getString("BookingID"));
                                E2.setText(snapshot.getString("PickupDate"));
                                E3.setText(snapshot.getString("Model"));

                            }
                        }
                    }
                });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OnGoing.this, Trip.class);
                intent.putExtra("EMAIL", Email);
                startActivity(intent);

            }
        });


    }


}