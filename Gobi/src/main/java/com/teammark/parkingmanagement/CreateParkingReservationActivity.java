package com.teammark.parkingmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.teammark.parkingmanagement.model.ParkingArea;
import com.teammark.parkingmanagement.model.ParkingReservation;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class CreateParkingReservationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private EditText edtVehicleNumber, edtCustomerName, edtLicenceNumber, edtContactNumber, edtArrivalDate, edtArrivalTime;

    private RadioButton radEVCYes, radEVCNo;

    private Button btnReserveParkingSlot;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_reservation);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();

        String slotID = bundle.getString("slotID");
        String slotTitle = bundle.getString("slotTitle");

        edtVehicleNumber = findViewById(R.id.edtVehicleNumber);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtLicenceNumber = findViewById(R.id.edtLicenceNumber);
        edtContactNumber = findViewById(R.id.edtContactNumber);

        radEVCYes = findViewById(R.id.radEVCYes);
        radEVCNo = findViewById(R.id.radEVCNo);

        edtArrivalDate = findViewById(R.id.edtArrivalDate);
        edtArrivalTime = findViewById(R.id.edtArrivalTime);

        btnReserveParkingSlot = findViewById(R.id.btnReserveParkingSlot);

        edtArrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        edtArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btnReserveParkingSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reservationID = UUID.randomUUID().toString();

                String vehicleNumber = edtVehicleNumber.getText().toString();
                String customerName = edtCustomerName.getText().toString();
                String licenceNumber = edtLicenceNumber.getText().toString();
                String contactNumber = edtContactNumber.getText().toString();
                String arrivalDate = edtArrivalDate.getText().toString();
                String arrivalTime = edtArrivalTime.getText().toString();

                String evcRequire = "No";

                if(radEVCYes.isChecked()){
                    evcRequire = "Yes";
                }else {
                    evcRequire = "No";
                }

                ParkingReservation reservation = new ParkingReservation(reservationID, slotID, slotTitle, vehicleNumber,
                        customerName, licenceNumber, contactNumber, arrivalDate, arrivalTime, evcRequire);

                saveToFireStore(reservation);
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
        edtArrivalDate.setText(date);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        edtArrivalTime.setText(time);
    }

    private void saveToFireStore(ParkingReservation reservation){
        if(reservation != null){
            HashMap<String, Object> parkingReservationMap = new HashMap<>();

            parkingReservationMap.put("reservationID", reservation.getReservationID());
            parkingReservationMap.put("slotID", reservation.getSlotID());
            parkingReservationMap.put("slotTitle", reservation.getSlotTitle());
            parkingReservationMap.put("vehicleNumber", reservation.getVehicleNumber());
            parkingReservationMap.put("customerName", reservation.getCustomerName());
            parkingReservationMap.put("licenceNumber", reservation.getLicenceNumber());
            parkingReservationMap.put("contactNumber", reservation.getArrivalDate());
            parkingReservationMap.put("arrivalDate", reservation.getArrivalDate());
            parkingReservationMap.put("arrivalTime", reservation.getArrivalTime());
            parkingReservationMap.put("evcRequire", reservation.getEvcRequire());

            db.collection("ParkingReservations").document(reservation.getReservationID()).set(parkingReservationMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateParkingReservationActivity.this, "Reservation Successful !!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateParkingReservationActivity.this, ViewReservationActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(CreateParkingReservationActivity.this, "Reservation Failed !!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateParkingReservationActivity.this, ViewReservationActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this, "Please fill necessary fields", Toast.LENGTH_SHORT).show();
        }
    }
}