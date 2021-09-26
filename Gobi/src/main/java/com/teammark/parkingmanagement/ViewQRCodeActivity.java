package com.teammark.parkingmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.teammark.parkingmanagement.util.QRGenerator;

public class ViewQRCodeActivity extends AppCompatActivity {

    ImageView imgscanQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qrcode);

        Intent intent = getIntent();

        String reservationID = intent.getStringExtra("reservationID");

        imgscanQR = findViewById(R.id.imgscanQR);

        QRGenerator scanQR = new QRGenerator();

        scanQR.generateQR(reservationID, imgscanQR);
    }
}