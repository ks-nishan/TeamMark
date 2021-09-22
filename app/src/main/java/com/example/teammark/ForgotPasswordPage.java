package com.example.teammark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPage extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        emailEditText = (EditText)findViewById(R.id.editEmail);
        resetPasswordButton = (Button)findViewById(R.id.btnUpdatePassword);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailEditText.setError("Please enter the Email address");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter the valid email address");
            emailEditText.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordPage.this, "Check your email to reset the password", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPasswordPage.this, "Try Again!somthing wrong happend", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}