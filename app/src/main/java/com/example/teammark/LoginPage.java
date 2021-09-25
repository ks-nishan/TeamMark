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

import com.example.myproject.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{

    private TextView register,forgotPassword;
    private EditText editTextEmail,editTextPassword;
    private Button signIn;
    Button testBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        register = (TextView)findViewById(R.id.txtRegister);
        register.setOnClickListener(this);

        signIn = (Button)findViewById(R.id.btnsignIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.edtEmail);
        editTextPassword = (EditText)findViewById(R.id.edtPassword);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView)findViewById(R.id.txtforgotPassword);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtRegister:
                startActivity(new Intent(this,RegisterPage.class));
                break;

            case R.id.btnsignIn:

                userLogin();
                break;

            case R.id.txtforgotPassword:
                startActivity(new Intent(LoginPage.this,ForgotPasswordPage.class));
                break;
        }

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Provide a valid email address");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Please provide a valid password!!!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        Intent intent = new Intent(LoginPage.this,MainActivity.class);
                        intent.putExtra("EMAIL",email);
                        startActivity(intent);
                        //startActivity(new Intent(LoginPage.this,MainActivity.class));
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginPage.this, "Check Your email to verify your account", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(LoginPage.this, "Failed to login Please check your credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}