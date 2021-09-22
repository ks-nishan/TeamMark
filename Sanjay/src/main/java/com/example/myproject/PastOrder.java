package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PastOrder extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] place = {"","Colombo", "Malabe", "Kalutara", "Negombo", "Galle"};
    private FirebaseFirestore db;
    final Calendar myCalendar = Calendar.getInstance();
    public static String location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order);
        db = FirebaseFirestore.getInstance();
        Button btn1, btn2;
        EditText in1, in3;
        EditText ou1, ou2, ou3, ou4;
        TextView T1, T2;

        btn1 = findViewById(R.id.PastOrder_SearchButton);
        btn2 = findViewById(R.id.PastOrder_Deletebutton);
        in1 = findViewById(R.id.Search_Date);

        in3 = findViewById(R.id.Search_MODEL);
        T1 = findViewById(R.id.PastOrder_Date);
        ou1 = findViewById(R.id.PastOrder_bookingId);
        ou2 = findViewById(R.id.PastOrder_phone);
        ou3 = findViewById(R.id.PastOrder_Model);
        ou4 = findViewById(R.id.PastOrder_Location);
        T2 = findViewById(R.id.PastOrder_Total);






        Spinner spin = (Spinner) findViewById(R.id.Search_Date_Location);
        spin.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, place);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();


            }
        };
        EditText edittext = findViewById(R.id.Search_Date);
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PastOrder.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = in1.getText().toString();
                String Location = location;
                String Model = in3.getText().toString();

                if (!Date.isEmpty() && !Location.isEmpty() && !Model.isEmpty()) {


                    db.collection("Vehicle_Booking").whereEqualTo("PickupDate", Date).whereEqualTo("Location", Location).whereEqualTo("Model", Model)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (DocumentSnapshot snapshot : task.getResult()) {
                                            String id = snapshot.getString("BookingID");


                                            ou1.setText(snapshot.getString("BookingID"));
                                            ou3.setText(snapshot.getString("Model"));
                                            ou4.setText(snapshot.getString("Location"));
                                            T1.setText(snapshot.getString("PickupDate"));
                                            T2.setText(snapshot.getString("Total"));
                                            String Email = snapshot.getString("Email");
                                            db.collection("Customers").whereEqualTo("Email", Email)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                for (DocumentSnapshot snapshot : task.getResult()) {


                                                                    ou2.setText(snapshot.getString("PhoneNumber"));


                                                                }
                                                            }
                                                        }
                                                    });


                                        }
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {

                            ou1.setText("Not Found");
                            ou3.setText("Not Found");
                            ou4.setText("Not Found");
                            T1.setText("Not Found");
                            T2.setText("Not Found");


                        }
                    });


                } else {

                    Toast.makeText(PastOrder.this, "Check your fields !", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ou1.getText().toString();
                if (!id.isEmpty())
                deleteData(id);


            }
        });


    }


    public void deleteData(String id) {

        db.collection("Vehicle_Booking").document(id).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(PastOrder.this, "Data Deleted !", Toast.LENGTH_SHORT).show();

                            EditText ou1, ou2, ou3, ou4;
                            TextView T1, T2;

                            ou1 = findViewById(R.id.PastOrder_bookingId);
                            ou2 = findViewById(R.id.PastOrder_phone);
                            ou3 = findViewById(R.id.PastOrder_Model);
                            ou4 = findViewById(R.id.PastOrder_Location);
                            T2 = findViewById(R.id.PastOrder_Total);
                            T1 = findViewById(R.id.PastOrder_Date);

                            ou1.setText("");
                            ou3.setText("");
                            ou4.setText("");
                            T1.setText("");
                            T2.setText("");
                            ou2.setText("");
                        } else {
                            Toast.makeText(PastOrder.this, "Data not Deleted !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        EditText edittext = findViewById(R.id.Search_Date);
        edittext.setText(sdf.format(myCalendar.getTime()));

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        location = place[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        location = "null";
    }


}