package com.teammark.parkingmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.teammark.parkingmanagement.model.ParkingArea;
import com.teammark.parkingmanagement.model.ParkingReservation;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class UpdateParkingReservationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private TextView parkingSlot;

    private EditText updVehicleNumber, updCustomerName, updLicenceNumber, updContactNumber, updArrivalDate, updArrivalTime;

    private RadioButton updEVCYes, updEVCNo;

    private Button btnUpdateReservation;

    private FirebaseFirestore db;

    String uReservationID, uSlotID, uSlotTitle, uVehicleNumber, uCustomerName, uLicenceNumber, uContactNumber, uEvcRequired, uArrivalDate, uArrivalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_reservation);

        db = FirebaseFirestore.getInstance();

        updVehicleNumber = findViewById(R.id.edtVehicleNumber);
        updCustomerName = findViewById(R.id.edtCustomerName);
        updLicenceNumber = findViewById(R.id.edtLicenceNumber);
        updContactNumber = findViewById(R.id.edtContactNumber);

        updEVCYes = findViewById(R.id.radEVCYes);
        updEVCNo = findViewById(R.id.radEVCNo);

        updArrivalDate = findViewById(R.id.edtArrivalDate);
        updArrivalTime = findViewById(R.id.edtArrivalTime);

        btnUpdateReservation = findViewById(R.id.btnReserveParkingSlot);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            uReservationID = bundle.getString("uReservationID");

            uSlotID = bundle.getString("uSlotID");
            uSlotTitle = bundle.getString("uSlotTitle");

            System.out.println(bundle.getString("uVehicleNumber"));

            updVehicleNumber.setText(bundle.getString("uVehicleNumber"));
            updCustomerName.setText(bundle.getString("uCustomerName"));
            updLicenceNumber.setText(bundle.getString("uLicenceNumber"));
            updContactNumber.setText(bundle.getString("uContactNumber"));

            updArrivalDate.setText(bundle.getString("uArrivalDate"));
            updArrivalTime.setText(bundle.getString("uArrivalTime"));

            updEVCYes = findViewById(R.id.radEVCYes);
            updEVCNo = findViewById(R.id.radEVCNo);

            if(bundle.getString("uEvcRequire").equalsIgnoreCase("Yes")){
                updEVCYes.setChecked(true);
            }else{
                updEVCNo.setChecked(true);
            }
        }

        updArrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        updArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btnUpdateReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uVehicleNumber = updVehicleNumber.getText().toString();
                uCustomerName = updCustomerName.getText().toString();
                uLicenceNumber = updLicenceNumber.getText().toString();
                uContactNumber = updContactNumber.getText().toString();
                uArrivalDate = updArrivalDate.getText().toString();
                uArrivalTime = updArrivalTime.getText().toString();

                String evcRequire = "No";

                if(updEVCYes.isChecked()){
                    uEvcRequired = "Yes";
                }else {
                    uEvcRequired = "No";
                }

                ParkingReservation parkingReservation = new ParkingReservation(
                        uReservationID, uSlotID, uSlotTitle, uVehicleNumber, uCustomerName,
                        uLicenceNumber, uContactNumber, uArrivalDate, uArrivalTime, uEvcRequired);

                updateToFireStore(parkingReservation);
            }
        });
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );

        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = month + "/" + dayOfMonth + "/" + year;
        updArrivalDate.setText(date);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        updArrivalTime.setText(time);
    }

    private void updateToFireStore(ParkingReservation reservation){
        if(reservation != null){
            HashMap<String, Object> parkingReservationMap = new HashMap<>();

            String reservationID = reservation.getReservationID();

            String slotID = reservation.getSlotID();
            String slotTitle = reservation.getSlotTitle();

            String vehicleNumber = reservation.getVehicleNumber();
            String customerName = reservation.getCustomerName();
            String licenceNumber = reservation.getLicenceNumber();
            String contactNumber = reservation.getContactNumber();

            String arrivalDate = reservation.getArrivalDate();
            String arrivalTime = reservation.getArrivalTime();
            String evcRequire = reservation.getEvcRequire();


            db.collection("ParkingReservations").document(reservationID).update("slotID",
                    slotID, "slotTitle", slotTitle, "vehicleNumber", vehicleNumber,
                    "customerName", customerName, "licenceNumber", licenceNumber, "contactNumber", contactNumber,
                    "arrivalDate", arrivalDate, "arrivalTime", arrivalTime, "evcRequire", evcRequire)

                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateParkingReservationActivity.this, "Update Successfull!!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateParkingReservationActivity.this, ViewReservationActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(UpdateParkingReservationActivity.this, "Update Failed !!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateParkingReservationActivity.this, ViewReservationActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this, "Please fill necessary fields", Toast.LENGTH_SHORT).show();
        }
    }
}