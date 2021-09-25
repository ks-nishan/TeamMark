package com.example.myproject;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Booking extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] place = {"Colombo", "Malabe", "Kalutara", "Negombo", "Galle"};
    public static String location = "";
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    static String Email;
    static String Model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

//checking

        Intent intent2 = getIntent();
        Email = intent2.getStringExtra("EMAIL");
        Model = intent2.getStringExtra("Model");

        EditText editText6 = findViewById(R.id.Car_Model);
        editText6.setText(Model);


        Spinner spin = (Spinner) findViewById(R.id.spinner);
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
        EditText edittext = findViewById(R.id.PickUpDate);
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Booking.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, month);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();


            }
        };
        EditText edittext2 = findViewById(R.id.ReturnDate);
        edittext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Booking.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Button btn1;
        EditText edittext4 = findViewById(R.id.ReturnDate);
        EditText edittext3 = findViewById(R.id.PickUpDate);
        EditText editText5 = findViewById(R.id.Calculate_Coupon);
        TextView input1 = findViewById(R.id.Charge_of_vehicle);
        TextView input2 = findViewById(R.id.Calculate_Tax);
        TextView input3 = findViewById(R.id.Calculate_Discount);
        TextView input4 = findViewById(R.id.Calculate_Total);
        btn1 = findViewById(R.id.Calculate_Booking);


        btn1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//calculation for vehicle rental

                CalculateMoney obj = new CalculateMoney();
                try {

                    float charge = obj.Calculate(edittext3.getText().toString(), edittext4.getText().toString());
                    String charge2 = Float.toString(charge);
                    float tax = obj.Tax(charge);
                    String tax2 = Float.toString(tax);

                    Float coupon = obj.Coupon(editText5.getText().toString());
                    String coupon2 = Float.toString(coupon);
                    Float total = charge + tax - coupon;
                    String total2 = Float.toString(total);
                    if (total > 0) {
                        input1.setText(charge2);
                        input2.setText(tax2);
                        input3.setText(coupon2);
                        input4.setText(total2);
                    } else {

                        Toast.makeText(Booking.this, "Check Your Dates", Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        });


        Button btn2;
        btn2 = findViewById(R.id.Reserve_Button);


        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                StartReserve();


            }


        });


    }

    public void StartReserve() {
        EditText edittext4 = findViewById(R.id.ReturnDate);
        EditText edittext3 = findViewById(R.id.PickUpDate);
        TextView input4 = findViewById(R.id.Calculate_Total);
        String total = input4.getText().toString();


        if (!total.isEmpty()) {

            Intent intent = new Intent(Booking.this, Payment.class);
            intent.putExtra("TOTAL", total);
            intent.putExtra("DATE1", edittext3.getText().toString());
            intent.putExtra("DATE2", edittext4.getText().toString());
            intent.putExtra("LOCATION", location);
            intent.putExtra("MODEL", Model);
            intent.putExtra("EMAIL", Email);
            startActivity(intent);
        } else {

            Toast.makeText(Booking.this, "Check Your Dates", Toast.LENGTH_SHORT).show();


        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        location = place[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        location = "null";
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        EditText edittext = findViewById(R.id.PickUpDate);
        edittext.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel2() {
        String myFormat2 = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat2, Locale.US);
        EditText edittext2 = findViewById(R.id.ReturnDate);
        edittext2.setText(sdf.format(myCalendar2.getTime()));

    }


};



