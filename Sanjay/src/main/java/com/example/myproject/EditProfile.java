package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        EditText input1, input2, input3, input4;
        TextView input5;
        Button btn1;
        setContentView(R.layout.activity_edit_profile);
        Intent myIntent = getIntent();
        String Username = myIntent.getStringExtra("USERNAME");
        String Email = myIntent.getStringExtra("EMAIL");
        String Password = myIntent.getStringExtra("PS");
        String PhoneNumber = myIntent.getStringExtra("PHONE");

        input1 = findViewById(R.id.E_UserName);
        input2 = findViewById(R.id.E_Email);
        input3 = findViewById(R.id.E_Password);
        input4 = findViewById(R.id.E_Phone);
        input5 = findViewById(R.id.P_Username3);
        btn1 = findViewById(R.id.E_SaveButton);

        input5.setText(Username);
        input1.setText(Username);
        input2.setText(Email);
        input2.setEnabled(false);
        input3.setText(Password);
        input4.setText(PhoneNumber);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



          UpdateProfile(input2.getText().toString(),input1.getText().toString(),input4.getText().toString(),input3.getText().toString());


            }
        });



    }


    private void UpdateProfile (String email, String username , String phone ,String password ){

        if (!email.isEmpty()&& !phone.isEmpty()&& !username.isEmpty() && !password.isEmpty()) {
            HashMap <String,Object> map = new HashMap<>();

            map.put("UserName",username);
            map.put("Email",email);
            map.put("PhoneNumber",phone);
            map.put("Password",password);




            db.collection("Customers").document(email).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(EditProfile.this,"Updated",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfile.this, Profile.class);
                                intent.putExtra("EMAIL",email);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {

                    Toast.makeText(EditProfile.this,"Data not saved",Toast.LENGTH_SHORT).show();
                }
            });



        }
        else {
            Toast.makeText(this,"cannot be empty",Toast.LENGTH_LONG).show();
        }




    }



}