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
import android.util.Log;
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
import com.teammark.parkingmanagement.TouchHelper.TouchHelperArea;
import com.teammark.parkingmanagement.model.ParkingArea;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewParkingAreaActivity extends AppCompatActivity {

    private EditText searchArea;
    private ProgressBar prgAreaList;
    private Button btnAddArea;
    private RecyclerView listParkingAreas;
    private FirebaseFirestore db;
    private AdapterParkingArea adapterParkingArea;
    private List<ParkingArea> areaList;

    private BottomNavigationView bottomNavAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parking_area);

        searchArea = findViewById(R.id.searchArea);

        searchArea.addTextChangedListener(new TextWatcher() {
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

        listParkingAreas = findViewById(R.id.listParkingAreas);
        listParkingAreas.setHasFixedSize(true);
        listParkingAreas.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        areaList = new ArrayList<>();
        adapterParkingArea = new AdapterParkingArea(this, areaList);
        listParkingAreas.setAdapter(adapterParkingArea);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelperArea(adapterParkingArea));
        touchHelper.attachToRecyclerView(listParkingAreas);

        fetchData();

        btnAddArea = findViewById(R.id.btnAddArea);

        btnAddArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewParkingAreaActivity.this, CreateParkingAreaActivity.class);
                startActivity(intent);
            }
        });

        View admList = findViewById(R.id.adminList);

        admList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewParkingAreaActivity.this, ViewParkingAreaActivity.class);
                startActivity(intent);
            }
        });

        View createPrkForm = findViewById(R.id.createParkingForm);

        createPrkForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewParkingAreaActivity.this, CreateParkingAreaActivity.class);
                startActivity(intent);
            }
        });

        View customerUser = findViewById(R.id.customerUser);

        customerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewParkingAreaActivity.this, FindParkingSlotActivity.class);
                startActivity(intent);
            }
        });
    }

    public void fetchData(){
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

    private void searchFilter(String slot){
        ArrayList<ParkingArea> filteredList = new ArrayList<>();

        for (ParkingArea item : areaList){
            if (item.getParkingareaTitle().toLowerCase().toLowerCase().contains(slot.toLowerCase())){
                filteredList.add(item);
            }
        }

        adapterParkingArea.filterList(filteredList);
    }
}