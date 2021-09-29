package com.teammark.parkingmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.teammark.parkingmanagement.util.ParkingPaymentCalculator;

public class CalculateParkingPaymentActivity extends AppCompatActivity {

    EditText edtTotal, edtHours, edtMinutes;

    RadioButton radBike, radCar;

    CheckBox chkWattNone, chkWattMin, chkWattMed, chkWattMax;

    Button btnCalcTotal;

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
                double hours = Double.parseDouble(edtHours.getText().toString());
                double minutes = Double.parseDouble(edtMinutes.getText().toString());
                double vehicleFee = 0.0;
                double wattFee = 0.0;
                Double totalPayment = 0.0;

                if(radBike.isChecked()){
                    vehicleFee += feeBike;
                }else if(radCar.isChecked()){
                    vehicleFee += feeCar;
                }

                if(chkWattMax.isChecked()){
                    wattFee += 50;
                }

                if(chkWattMed.isChecked()){
                    wattFee += 37.5;
                }

                if(chkWattMin.isChecked()){
                    wattFee += 25;
                }

                totalPayment = new ParkingPaymentCalculator(hours, minutes, vehicleFee, wattFee).calcTotalPayment();
                edtTotal.setText(totalPayment.toString());
                Toast.makeText(CalculateParkingPaymentActivity.this, "Calculated...", Toast.LENGTH_SHORT).show();
                totalPayment = 0.0;
            }
        });
    }
}