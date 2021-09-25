package com.example.myproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Trip extends AppCompatActivity {
    private FirebaseFirestore db;

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);


        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        //initalize fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        //check permission

        if (ActivityCompat.checkSelfPermission(Trip.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //when permission granted
            //call  method
            Toast.makeText(this, "WELCOME", Toast.LENGTH_SHORT).show();
            currentLocation();


        } else {

            ActivityCompat.requestPermissions(Trip.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }



        db = FirebaseFirestore.getInstance();
        Intent myIntent = getIntent();
        String Email = myIntent.getStringExtra("EMAIL");
        TextView T1, T2,  T3 , T4;
        Button Btn1, btn2 ;

        T1 = findViewById( R.id.Trip_Location);
        T2 = findViewById( R.id.Trip_duration);
        T3 = findViewById( R.id.Trip_Username);
        T4 = findViewById( R.id.Trip_Model);

        Btn1 = findViewById(R.id.Trip_Park_button);
        btn2 = findViewById(R.id.Trip_End_button);



        db.collection("On_Going").whereEqualTo("Email", Email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (DocumentSnapshot snapshot : task.getResult()) {

                                T1.setText(snapshot.getString("Location"));
                                String pick = snapshot.getString("PickupDate");
                                String Return = snapshot.getString("ReturnDate");
                                try {
                                   int d = durationCalc(pick,Return);

                                  String duration = String.valueOf(d) ;
                                  T2.setText(duration + " Days");

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                T3.setText(snapshot.getString("BookingID"));
                                T4.setText(snapshot.getString("Model"));

                            }
                        }
                    }


                });



        Btn1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



    }
});

btn2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        db.collection("On_Going").document(Email).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                        }else{

                        }
                    }
                });




    }
});









    }

    public int durationCalc(String pick, String aReturn) throws ParseException {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d1 = sdf.parse(pick);
        Date d2 = sdf.parse(aReturn);

        long Time = d2.getTime() - d1.getTime();
        long Days = (Time
                / (1000 * 60 * 60 * 24))
                % 365;
        int Day = (int)Days;
        return  Day;
    }


    public void currentLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            supportMapFragment.getMapAsync(googleMap -> {

                                //initialize lat lng
                                LatLng latLng = new LatLng(location.getLatitude()
                                        ,location.getLongitude());

                                MarkerOptions options = new MarkerOptions().position(latLng)
                                        .title("You are here");

                                //zoom map

                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                                googleMap.addMarker(options);



                            });

                        }
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44){

            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                currentLocation();

            }

        }


    }




}