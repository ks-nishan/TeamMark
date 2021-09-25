package com.teammark.parkingmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class CalculateParkingPaymentActivity extends AppCompatActivity {

    EditText edtTotal, edtHours, edtMinutes;

    RadioButton radBike, radCar;

    CheckBox chkWattNone, chkWattMin, chkWattMed, chkWattMax;

    Button btnCalcTotal;

    Double totalPayment = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_parking_payment);

        Bundle bundleFee = getIntent().getExtras();

        double feeBike = Double.parseDouble(bundleFee.getString("feeBike"));
        double feeCar = Double.parseDouble(bundleFee.getString("feeCar"));

        edtTotal = findViewById(R.id.edtTotal);

        edtHours = findViewById(R.id.edtHours);
        edtMinutes = findViewById(R.id.edtMinutes);

        radBike = findViewById(R.id.radBike);
        radCar = findViewById(R.id.radCar);

        chkWattNone = findViewById(R.id.chkWattNone);
        chkWattMin = findViewById(R.id.chkWattMin);
        chkWattMed = findViewById(R.id.chkWattMed);
        chkWattMax = findViewById(R.id.chkWattMax);

        btnCalcTotal = findViewById(R.id.btnCalcTotal);

        btnCalcTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hours = Integer.parseInt(edtHours.getText().toString());
                int minutes = Integer.parseInt(edtMinutes.getText().toString());

                int time = (hours * 60) + minutes;

                if(radBike.isChecked()){
                    totalPayment += feeBike;
                }

                if(radCar.isChecked()){
                    totalPayment += feeCar;
                }

                totalPayment *= time;

                if(chkWattMax.isChecked()){
                    totalPayment += 500;
                }

                if(chkWattMed.isChecked()){
                    totalPayment += 250;
                }

                if(chkWattMin.isChecked()){
                    totalPayment += 100;
                }

                edtTotal.setText(totalPayment.toString());

                totalPayment = 0.0;
            }
        });
    }
}