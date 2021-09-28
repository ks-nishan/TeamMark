package com.teammark.parkingmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.teammark.parkingmanagement.Adapter.AdapterParkingArea;
import com.teammark.parkingmanagement.Adapter.AdapterReservation;
import com.teammark.parkingmanagement.TouchHelper.TouchHelperReservation;
import com.teammark.parkingmanagement.model.ParkingArea;
import com.teammark.parkingmanagement.model.ParkingReservation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewReservationActivity extends AppCompatActivity {

    private EditText searchReservation;
    private ProgressBar prgAreaList;
    private RecyclerView listParkingReservations;
    private FirebaseFirestore db;
    private AdapterReservation adapterReservation;
    private List<ParkingReservation> reservationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation);

        searchReservation = findViewById(R.id.searchReservation);

        searchReservation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchFilter(s.toString());
            }
        });

        prgAreaList = findViewById(R.id.prgAreaList);

        listParkingReservations = findViewById(R.id.listReservations);
        listParkingReservations.setHasFixedSize(true);
        listParkingReservations.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        reservationList = new ArrayList<>();
        adapterReservation = new AdapterReservation(ViewReservationActivity.this, reservationList);
        listParkingReservations.setAdapter(adapterReservation);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelperReservation(adapterReservation));
        touchHelper.attachToRecyclerView(listParkingReservations);

        fetchData();

        BottomNavigationView bottomNavCus = findViewById(R.id.bottomNavigationView2);

        bottomNavCus.setSelectedItemId(R.id.myReservatons);

        View cusList = findViewById(R.id.customerList);

        cusList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewReservationActivity.this, FindParkingSlotActivity.class);
                startActivity(intent);
            }
        });

        View resv = findViewById(R.id.myReservatons);

        resv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewReservationActivity.this, ViewReservationActivity.class);
                startActivity(intent);
            }
        });

        View logout = findViewById(R.id.adminUser);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewReservationActivity.this, ViewParkingAreaActivity.class);
                startActivity(intent);
            }
        });
    }


    public void fetchData(){
        db.collection("ParkingReservations").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        reservationList.clear();

                        for (DocumentSnapshot snapshot : task.getResult()){
                            ParkingReservation reservation = new ParkingReservation(
                                    snapshot.getString("reservationID"),
                                    snapshot.getString("slotID"),
                                    snapshot.getString("slotTitle"),
                                    snapshot.getString("vehicleNumber"),
                                    snapshot.getString("customerName"),
                                    snapshot.getString("licenceNumber"),
                                    snapshot.getString("contactNumber"),
                                    snapshot.getString("arrivalDate"),
                                    snapshot.getString("arrivalTime"),
                                    snapshot.getString("evcRequire"));

                            reservationList.add(reservation);
                        }
                        adapterReservation.notifyDataSetChanged();

                        prgAreaList.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(ViewReservationActivity.this, "No Parking Areas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchFilter(String slot){
        ArrayList<ParkingReservation> filteredList = new ArrayList<>();

        for (ParkingReservation item : reservationList){
            if (item.getSlotTitle().toLowerCase().toLowerCase().contains(slot.toLowerCase())){
                filteredList.add(item);
            }
        }

        adapterReservation.filterList(filteredList);
    }
}