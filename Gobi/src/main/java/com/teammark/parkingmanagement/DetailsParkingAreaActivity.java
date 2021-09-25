package com.teammark.parkingmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsParkingAreaActivity extends AppCompatActivity {

    private TextView txtAreaTitle, txtLocation, txtBikeFee, txtCarFee, txtChargingSpeed, txtConnector;

    private ImageView imgParkingSlot;

    private Button btnReserveArea, btnCalculate;

    private String dParkingareaID, dParkingAreaImg, dParkingareaTitle, dParkingareaAddress,
            dTypeBike, dTypeCar,  dCountBikeSlot, dCountCarSlot, dFeeBike, dFeeCar, dEVFacility,
            dWattMin,  dWattMed, dWattMax,  dConTwo, dConCombo, dConCha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_parking_area);

        txtAreaTitle = findViewById(R.id.txtAreaTitle);
        txtLocation = findViewById(R.id.txtLocation);
        txtBikeFee = findViewById(R.id.txtBikeFee);
        txtCarFee = findViewById(R.id.txtCarFee);
        txtChargingSpeed = findViewById(R.id.txtChargingSpeed);
        txtConnector = findViewById(R.id.txtConnector);
        imgParkingSlot = findViewById(R.id.imgParkingSlot);

        btnReserveArea = findViewById(R.id.btnReserveArea);
        btnCalculate = findViewById(R.id.btnCalculate);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            dParkingareaID = bundle.getString("dID");
            dParkingAreaImg = bundle.getString("dImg");
            dParkingareaTitle = bundle.getString("dTitle");
            dParkingareaAddress = bundle.getString("dAddress");

            dTypeBike = bundle.getString("dTypeBike");
            dTypeCar = bundle.getString("dTypeCar");

            dCountBikeSlot = bundle.getString("dCountBikeSlots");
            dCountCarSlot = bundle.getString("dCountCarSlots");

            dFeeBike = bundle.getString("dFeeBike");
            dFeeCar = bundle.getString("dFeeCar");

            dEVFacility = bundle.getString("dEVFacility");

            dWattMin = bundle.getString("dWattMin");
            dWattMed = bundle.getString("dWattMed");
            dWattMax = bundle.getString("dWattMax");

            dConTwo = bundle.getString("dConTypeTwo");
            dConCombo = bundle.getString("dConCombo");
            dConCha = bundle.getString("dConCHA");

            txtAreaTitle.setText(dParkingareaTitle);
            txtLocation.setText(dParkingareaAddress);
            txtBikeFee.setText("Rs " + dFeeBike);
            txtCarFee.setText("Rs "+ dFeeCar);

            StringBuilder chargingSpeed = new StringBuilder("");

            if (dWattMin.equalsIgnoreCase("Yes")){
                chargingSpeed.append(" 10,");
            }

            if (dWattMed.equalsIgnoreCase("Yes")){
                chargingSpeed.append(" 15,");
            }

            if (dWattMax.equalsIgnoreCase("Yes")){
                chargingSpeed.append(" 20");
            }

            txtChargingSpeed.setText(chargingSpeed);

            StringBuilder connectorType = new StringBuilder("Type");

            if (dConTwo.equalsIgnoreCase("Yes")){
                connectorType.append(" Two,");
            }

            if (dConCombo.equalsIgnoreCase("Yes")){
                connectorType.append(" Combo,");
            }

            if (dConCha.equalsIgnoreCase("Yes")){
                connectorType.append(" CHA");
            }

            txtConnector.setText(connectorType);

            Picasso.get()
                    .load(dParkingAreaImg)
                    .fit()
                    .centerCrop()
                    .into(imgParkingSlot);

            btnReserveArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailsParkingAreaActivity.this, CreateParkingReservationActivity.class);

                    Bundle bundleSlot = new Bundle();

                    bundleSlot.putString("slotID", dParkingareaID);
                    bundleSlot.putString("slotTitle", dParkingareaTitle);

                    intent.putExtras(bundleSlot);
                    startActivity(intent);
                }
            });

            btnCalculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailsParkingAreaActivity.this, CalculateParkingPaymentActivity.class);

                    Bundle bundleFee = new Bundle();

                    bundleFee.putString("feeBike", dFeeBike);
                    bundleFee.putString("feeCar", dFeeCar);

                    intent.putExtras(bundleFee);
                    startActivity(intent);
                }
            });
        }
    }
}