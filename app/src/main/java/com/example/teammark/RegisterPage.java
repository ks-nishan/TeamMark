package com.example.teammark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextUserName,editTextEmail,editTextPassword;
    private TextView registerUser,signIn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        registerUser = (Button)findViewById(R.id.btnRegister);
        registerUser.setOnClickListener(this);

        signIn = (TextView)findViewById(R.id.txtsignIn);
        signIn.setOnClickListener(this);

        editTextUserName = (EditText)findViewById(R.id.edtUserName);
        editTextEmail = (EditText)findViewById(R.id.edtEmail);
        editTextPassword = (EditText)findViewById(R.id.edtPassword);
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                registerUser();
                break;

            case R.id.txtsignIn:
                startActivity(new Intent(this,LoginPage.class));
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String username = editTextUserName.getText().toString().trim();

        if(username.isEmpty()){
            editTextUserName.setError("User Name is required");
            editTextUserName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email address is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }


        if(password.length()<6){
            editTextPassword.setError("minimum password length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(username,email);

                            HashMap<String,Object> map = new HashMap<>();

                            map.put("UserName",username);
                            map.put("Email",email);
                            map.put("Password",password);

                            db.collection("Customers").document(email).set(map);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterPage.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();

                                        //redirect to login
                                    }else{
                                        Toast.makeText(RegisterPage.this, "Faild to register! Try Again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(RegisterPage.this, "Faild to register! Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}