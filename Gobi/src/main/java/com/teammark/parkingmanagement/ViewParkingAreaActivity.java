package com.teammark.parkingmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.teammark.parkingmanagement.Adapter.AdapterParkingArea;
import com.teammark.parkingmanagement.model.ParkingArea;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewParkingAreaActivity extends AppCompatActivity {

    private ProgressBar prgAreaList;
    private Button btnAddArea;
    private RecyclerView listParkingAreas;
    private FirebaseFirestore db;
    private AdapterParkingArea adapterParkingArea;
    private List<ParkingArea> areaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parking_area);

        prgAreaList = findViewById(R.id.prgAreaList);

        listParkingAreas = findViewById(R.id.listParkingAreas);
        listParkingAreas.setHasFixedSize(true);
        listParkingAreas.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        areaList = new ArrayList<>();
        adapterParkingArea = new AdapterParkingArea(this, areaList);
        listParkingAreas.setAdapter(adapterParkingArea);

        fetchData();

        btnAddArea = findViewById(R.id.btnAddArea);

        btnAddArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewParkingAreaActivity.this, CreateParkingAreaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchData(){
        db.collection("ParkingAreas").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        areaList.clear();

                        for (DocumentSnapshot snapshot : task.getResult()){
                            ParkingArea parkingArea = new ParkingArea(
                                    snapshot.getString("id"),

                                    snapshot.getString("image"),
                                    snapshot.getString("title"),
                                    snapshot.getString("address"),

                                    ((Long) snapshot.get("typeBike")).intValue(),
                                    ((Long) snapshot.get("typeCar")).intValue(),

                                    ((Long) snapshot.get("countBikeSlots")).intValue(),
                                    ((Long) snapshot.get("countCarSlots")).intValue(),

                                    ((Double) snapshot.get("feeBike")),
                                    ((Double) snapshot.get("feeCar")),

                                    ((Long) snapshot.get("evFacility")).intValue(),

                                    ((Long) snapshot.get("wattMin")).intValue(),
                                    ((Long) snapshot.get("wattMed")).intValue(),
                                    ((Long) snapshot.get("wattMax")).intValue(),

                                    ((Long) snapshot.get("conTypeTwo")).intValue(),
                                    ((Long) snapshot.get("conCombo")).intValue(),
                                    ((Long) snapshot.get("conCHA")).intValue());

                            areaList.add(parkingArea);
                        }
                        adapterParkingArea.notifyDataSetChanged();

                        prgAreaList.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(ViewParkingAreaActivity.this, "No Parking Areas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}