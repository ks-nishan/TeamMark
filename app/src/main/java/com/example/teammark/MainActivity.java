package com.example.teammark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.Profile;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4,card5,card6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        card1 = (CardView)findViewById(R.id.cardRentACar);
        card2 = (CardView)findViewById(R.id.cardParkingSloat);
        card3 = (CardView)findViewById(R.id.cardResavation);
        card4 = (CardView)findViewById(R.id.cardFeedback);
        card5 = (CardView)findViewById(R.id.cardBuyACar);
        card6 = (CardView)findViewById(R.id.cardLogout);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.cardRentACar:
                String EMAIL;
                Intent intent2 = getIntent();
                EMAIL = intent2.getStringExtra("EMAIL");
                Toast.makeText(this, EMAIL, Toast.LENGTH_SHORT).show();
                i = new Intent (this, ShowActivity.class);
                i.putExtra("EMAIL",EMAIL);
                startActivity(i);
                break;

            case R.id.cardParkingSloat:
                i = new Intent(this,Admin_dashboad.class);
                startActivity(i);
                break;

            case R.id.cardResavation:
                //email Passing
                String Email;
                Intent intent = getIntent();
                Email = intent.getStringExtra("EMAIL");
                Toast.makeText(this, Email, Toast.LENGTH_SHORT).show();
                i = new Intent (this, Profile.class);
                i.putExtra("EMAIL",Email);
                startActivity(i);
                break;

            case R.id.cardFeedback:
                i = new Intent(this,RegisterPage.class);
                startActivity(i);
                break;

            case R.id.cardBuyACar:
                i = new Intent(this,ForgotPasswordPage.class);
                startActivity(i);
                break;

            case R.id.cardLogout:
//                i = new Intent(this,Admin_PostAD.class);
//                startActivity(i);
//                break;
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginPage.class));
        }
    }
}