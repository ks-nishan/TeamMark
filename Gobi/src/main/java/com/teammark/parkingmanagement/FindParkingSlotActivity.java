package com.teammark.parkingmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.teammark.parkingmanagement.Adapter.AdapterParkingSlot;
import com.teammark.parkingmanagement.model.ParkingArea;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FindParkingSlotActivity extends AppCompatActivity {

    private Button btnMyReservations;
    private ProgressBar prgAreaList;
    private RecyclerView listParkingSlots;
    private FirebaseFirestore db;
    private AdapterParkingSlot adapterParkingSlot;
    private List<ParkingArea> slotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_parking_slot);

        prgAreaList = findViewById(R.id.prgAreaList);

        listParkingSlots = findViewById(R.id.listParkingSlots);
        listParkingSlots.setHasFixedSize(true);
        listParkingSlots.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        slotList = new ArrayList<>();
        adapterParkingSlot = new AdapterParkingSlot(FindParkingSlotActivity.this, slotList);
        listParkingSlots.setAdapter(adapterParkingSlot);

        btnMyReservations = findViewById(R.id.btnMyReservations);

        btnMyReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindParkingSlotActivity.this, ViewReservationActivity.class);
                startActivity(intent);
            }
        });

        fetchData();

        BottomNavigationView bottomNavCus = findViewById(R.id.bottomNavigationView2);

        bottomNavCus.setSelectedItemId(R.id.customerDashboard);

        View cusDashboard = findViewById(R.id.customerDashboard);

        cusDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(FindParkingSlotActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        View resv = findViewById(R.id.myReservatons);

        resv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindParkingSlotActivity.this, ViewReservationActivity.class);
                startActivity(intent);
            }
        });

        View adminUser = findViewById(R.id.adminUser);

        adminUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindParkingSlotActivity.this, ViewParkingAreaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void fetchData(){
        db.collection("ParkingAreas").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        slotList.clear();

                        for (DocumentSnapshot snapshot : task.getResult()){
                            ParkingArea parkingArea = new ParkingArea(
                                    snapshot.getString("id"),

                                    snapshot.getString("image"),
                                    snapshot.getString("title"),
                                    snapshot.getString("address"),

                                    snapshot.getString("typeBike"),
                                    snapshot.getString("typeCar"),

                                    snapshot.getString("countBikeSlots"),
                                    snapshot.getString("countCarSlots"),

                                    snapshot.getString("feeBike"),
                                    snapshot.getString("feeCar"),

                                    snapshot.getString("evFacility"),

                                    snapshot.getString("wattMin"),
                                    snapshot.getString("wattMed"),
                                    snapshot.getString("wattMax"),

                                    snapshot.getString("conTypeTwo"),
                                    snapshot.getString("conCombo"),
                                    snapshot.getString("conCHA"));

                            slotList.add(parkingArea);
                        }

                        adapterParkingSlot.notifyDataSetChanged();

                        prgAreaList.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(FindParkingSlotActivity.this, "No Parking Areas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}