package com.example.teammark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private Myadapter adapter;
    private List<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new Myadapter(this,list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        showData();
    }
    public void showData(){
        db.collection("Advertisements").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            Model model = new Model(snapshot.getString("id"),snapshot.getString("title"),snapshot.getString("district"),snapshot.getString("person"),snapshot.getString("amount"),snapshot.getString("mobile"),snapshot.getString("engine"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(ShowActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}