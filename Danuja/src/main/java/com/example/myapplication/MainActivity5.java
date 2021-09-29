package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity5 extends AppCompatActivity {
    EditText cid, cmt;
    Button update,delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        cid = (EditText) findViewById(R.id.custId);
        cmt = (EditText) findViewById(R.id.comments);
        update = (Button) findViewById(R.id.btn_update);
        delete =(Button) findViewById(R.id.btn_delete);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Feedback");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String cusID = snapshot.child("C01").child("cusID").getValue().toString();
                String comment = snapshot.child("C01").child("comment").getValue().toString();


                cid.setText(cusID);
                cmt.setText(comment);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String custId = cid.getText().toString();
                String data = cmt.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("cusID",custId);
                hashMap.put("comment", data);

                ref.child("C01").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(MainActivity5.this,"FeedBack Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("C01").child("cusID").removeValue();
                ref.child("C01").child("comment").removeValue();

                Toast.makeText(MainActivity5.this,"FeedBack Deleted", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void fina(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

    }
}
