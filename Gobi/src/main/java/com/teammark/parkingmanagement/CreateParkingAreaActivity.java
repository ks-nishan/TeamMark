package com.teammark.parkingmanagement;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.teammark.parkingmanagement.model.ParkingArea;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class CreateParkingAreaActivity extends AppCompatActivity {

    // Views declarations
    private EditText edtTitle, edtAddress, edtCountBikeSlots, edtCountCarSlots, edtFeeBike, edtFeeCar;

    private CheckBox chkTypeBike, chkTypeCar, chkWattMin, chkWattMed, chkWattMax, chkConTypeTwo, chkConCombo, chkConCHA;

    private RadioButton radEVYes, radEVNo;

    private Button btnCreateArea, btnCancel;

    private ImageView imgParking;

    private TextView txtTitleParking;

    private Uri imgParkingUri;

    private ProgressBar prgCreateArea;

    // DB Declaration
    private FirebaseFirestore db;

    // Storage-Reference Declaration
    private StorageReference storage;

    // ParkingArea Object instantiation
    private ParkingArea parkingArea;

    // ActivityResultLauncher<Intent> Object instantiation
    private ActivityResultLauncher<Intent> fileSelectorLauncher;

    private String uParkingareaID, uParkingAreaImg, uParkingareaTitle, uParkingareaAddress, uTypeBike, uTypeCar,  uCountBikeSlot, uCountCarSlot, uFeeBike, uFeeCar, uEVFacility,  uWattMin,  uWattMed, uWattMax,  uConTwo, uConCombo, uConCha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_area);

        // EditTexts
        edtTitle = findViewById(R.id.edtTitle);
        edtAddress = findViewById(R.id.edtAddress);

        edtCountBikeSlots = findViewById(R.id.edtCountBikeSlots);
        edtCountCarSlots = findViewById(R.id.edtCountCarSlots);

        edtFeeBike = findViewById(R.id.edtFeeBike);
        edtFeeCar = findViewById(R.id.edtFeeCar);

        // CheckBoxes
        chkTypeBike = findViewById(R.id.chkTypeBike);
        chkTypeCar = findViewById(R.id.chkTypeCar);

        chkWattMin = findViewById(R.id.chkWattMin);
        chkWattMed = findViewById(R.id.chkWattMed);
        chkWattMax = findViewById(R.id.chkWattMax);

        chkConTypeTwo = findViewById(R.id.chkConTypeTwo);
        chkConCombo = findViewById(R.id.chkConCombo);
        chkConCHA = findViewById(R.id.chkConCHA);

        // Radio Buttons
        radEVYes = findViewById(R.id.radEVYes);
        radEVNo = findViewById(R.id.radEVNo);

        // Buttons
        btnCreateArea = findViewById(R.id.btnCreateArea);
        btnCancel = findViewById(R.id.btnCancel);

        // ImageViews
        imgParking = findViewById(R.id.imgParking);

        // TextView
        txtTitleParking = findViewById(R.id.titleParking);

        // ProgressBar
        prgCreateArea = findViewById(R.id.prgCreateArea);

        // Database initialization
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            btnCreateArea.setText("Update");

            txtTitleParking.setText("Update Parking Area");

            uParkingareaID = bundle.getString("uID");
            uParkingAreaImg=bundle.getString("uImg");
            uParkingareaTitle=bundle.getString("uTitle");
            uParkingareaAddress=bundle.getString("uAddress");

            uTypeBike=bundle.getString("uTypeBike");
            uTypeCar=bundle.getString("uTypeCar");

            uCountBikeSlot=bundle.getString("uCountBikeSlots");
            uCountCarSlot=bundle.getString("uCountCarSlots");

            uFeeBike=bundle.getString("uFeeBike");
            uFeeCar=bundle.getString("uFeeCar");

            uEVFacility=bundle.getString("uEVFacility");

            uWattMin=bundle.getString("uWattMin");
            uWattMed=bundle.getString("uWattMed");
            uWattMax=bundle.getString("uWattMax");

            uConTwo=bundle.getString("uConTypeTwo");
            uConCombo=bundle.getString("uConCombo");
            uConCha=bundle.getString("uConCHA");

            edtTitle.setText(uParkingareaTitle);
            edtAddress.setText(uParkingareaAddress);
            edtCountBikeSlots.setText(uCountBikeSlot);
            edtCountCarSlots.setText(uCountCarSlot);
            edtFeeBike.setText(uFeeBike);
            edtFeeCar.setText(uFeeCar);

            if(uTypeBike.equalsIgnoreCase("Yes")){
                chkTypeBike.setChecked(true);
            }

            if (uEVFacility.equalsIgnoreCase("Yes")){
                radEVYes.setChecked(true);
            }else{
                radEVNo.setChecked(true);
            }

            if (uTypeCar.equalsIgnoreCase("Yes")){
                chkTypeCar.setChecked(true);
            }

            if(uWattMin.equalsIgnoreCase("Yes")){
                chkWattMin.setChecked(true);
            }

            if(uWattMed.equalsIgnoreCase("Yes")){
                chkWattMed.setChecked(true);
            }

            if(uWattMax.equalsIgnoreCase("Yes")){
                chkWattMax.setChecked(true);
            }

            if(uConTwo.equalsIgnoreCase("Yes")){
                chkConTypeTwo.setChecked(true);
            }

            if(uConCombo.equalsIgnoreCase("Yes")){
                chkConCombo.setChecked(true);
            }

            if(uConCha.equalsIgnoreCase("Yes")){
                chkConCHA.setChecked(true);
            }

            Picasso.get()
                    .load(uParkingAreaImg)
                    .fit()
                    .centerCrop()
                    .into(imgParking);

        }else{
            btnCreateArea.setText("Create");
            txtTitleParking.setText("Create New Parking Area");
        }

        // storage initialization
        storage = FirebaseStorage.getInstance().getReference("ParkingAreas");

        imgParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileSelecter();
            }
        });

        fileSelectorLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            imgParkingUri = data.getData();

                            Picasso.get()
                                    .load(imgParkingUri)
                                    .fit()
                                    .centerCrop()
                                    .into(imgParking);
                        }
                    }
                });


        // get data from view
        btnCreateArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id;

                if(bundle != null){
                    id = uParkingareaID;
                }else {
                    id = UUID.randomUUID().toString();
                }

                String title = edtTitle.getText().toString();
                String address = edtAddress.getText().toString();

                String countBikeSlots = edtCountBikeSlots.getText().toString();
                String countCarSlots = edtCountCarSlots.getText().toString();

                String feeBike = edtFeeBike.getText().toString();
                String feeCar = edtFeeCar.getText().toString();

                String typeBike;
                String typeCar;

                if(chkTypeBike.isChecked()){
                    typeBike = "Yes";
                }else {
                    typeBike = "No";
                }

                if(chkTypeCar.isChecked()){
                    typeCar = "Yes";
                }else {
                    typeCar = "No";
                }

                String wattMin;
                String wattMed;
                String wattMax;

                if(chkWattMin.isChecked()){
                    wattMin = "Yes";
                }else {
                    wattMin = "No";
                }

                if(chkWattMed.isChecked()){
                    wattMed = "Yes";
                }else {
                    wattMed = "No";
                }

                if(chkWattMax.isChecked()){
                    wattMax = "Yes";
                }else {
                    wattMax = "No";
                }


                String conTypeTwo;
                String conCombo;
                String conCHA;

                if(chkConTypeTwo.isChecked()){
                    conTypeTwo = "Yes";
                }else {
                    conTypeTwo = "No";
                }

                if(chkConCombo.isChecked()){
                    conCombo = "Yes";
                }else {
                    conCombo = "No";
                }

                if(chkConCHA.isChecked()){
                    conCHA = "Yes";
                }else {
                    conCHA = "No";
                }

                String evFacility;

                if (radEVYes.isChecked()){
                    evFacility = "Yes";
                }else{
                    evFacility = "No";
                }

                parkingArea = new ParkingArea(id, title, address, typeBike, typeCar,
                        countBikeSlots, countCarSlots, feeBike, feeCar, evFacility,
                        wattMin, wattMed, wattMax, conTypeTwo, conCombo, conCHA);

                Bundle bundleupd = getIntent().getExtras();

                if(imgParkingUri != null){
                    String childPath = System.currentTimeMillis() + "." + getFileExtension(imgParkingUri);
                    StorageReference fileReference = storage.child(childPath);

                    saveToFireStorage(fileReference, imgParkingUri, childPath, bundleupd);
                } else {
                    Toast.makeText(CreateParkingAreaActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openFileSelecter(){
        Intent fileIntent = new Intent();
        fileIntent.setType("image/*");
        fileIntent.setAction(Intent.ACTION_GET_CONTENT);
        fileSelectorLauncher.launch(fileIntent);
    }

    private void saveToFireStore(ParkingArea parkingArea){
        if(parkingArea != null){
            HashMap<String, Object> parkingAreaMap = new HashMap<>();

            parkingAreaMap.put("id", parkingArea.getParkingareaID());

            parkingAreaMap.put("image", parkingArea.getParkingAreaImg());
            parkingAreaMap.put("title", parkingArea.getParkingareaTitle());
            parkingAreaMap.put("address", parkingArea.getParkingareaAddress());

            parkingAreaMap.put("typeBike", parkingArea.getTypeBike());
            parkingAreaMap.put("typeCar", parkingArea.getTypeCar());

            parkingAreaMap.put("countBikeSlots", parkingArea.getCountBikeSlot());
            parkingAreaMap.put("countCarSlots", parkingArea.getCountCarSlot());

            parkingAreaMap.put("feeBike", parkingArea.getFeeBike());
            parkingAreaMap.put("feeCar", parkingArea.getFeeCar());

            parkingAreaMap.put("evFacility", parkingArea.getEvFacility());

            parkingAreaMap.put("wattMin", parkingArea.getWattMin());
            parkingAreaMap.put("wattMed", parkingArea.getWattMed());
            parkingAreaMap.put("wattMax", parkingArea.getWattMax());

            parkingAreaMap.put("conTypeTwo", parkingArea.getConTwo());
            parkingAreaMap.put("conCombo", parkingArea.getConCombo());
            parkingAreaMap.put("conCHA", parkingArea.getConCha());


            db.collection("ParkingAreas").document(parkingArea.getParkingareaID()).set(parkingAreaMap)
                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull @NotNull Task<Void> task) {
                          if (task.isSuccessful()) {
                              Toast.makeText(CreateParkingAreaActivity.this, "Created Successfully !!!", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(CreateParkingAreaActivity.this, ViewParkingAreaActivity.class);
                              startActivity(intent);
                          }
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(CreateParkingAreaActivity.this, "Creation Failed !!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateParkingAreaActivity.this, ViewParkingAreaActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this, "Please fill necessary fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateToFireStore(ParkingArea parkingArea){
        if(parkingArea != null){
            String id =  parkingArea.getParkingareaID();

            String image = parkingArea.getParkingAreaImg();
            String title = parkingArea.getParkingareaTitle();
            String address = parkingArea.getParkingareaAddress();

            String typeBike = parkingArea.getTypeBike();
            String typeCar = parkingArea.getTypeCar();

            String countBikeSlots = parkingArea.getCountBikeSlot();
            String countCarSlots = parkingArea.getCountCarSlot();

            String feeBike = parkingArea.getFeeBike();
            String feeCar = parkingArea.getFeeCar();

            String evFacility = parkingArea.getEvFacility();

            String wattMin = parkingArea.getWattMin();
            String wattMed = parkingArea.getWattMed();
            String wattMax = parkingArea.getWattMax();

            String conTypeTwo = parkingArea.getConTwo();
            String conCombo = parkingArea.getConCombo();
            String conCHA = parkingArea.getConCha();


            db.collection("ParkingAreas").document(id).update("image", image, "title", title, "address", address,
                    "typeBike", typeBike, "typeCar", typeCar, "countBikeSlots", countBikeSlots, "countCarSlots", countCarSlots,
                    "feeBike", feeBike, "feeCar", feeCar, "evFacility", evFacility, "wattMin", wattMin, "wattMed", wattMed, "wattMax", wattMax,
                    "conTypeTwo", conTypeTwo, "conCombo", conCombo, "conCHA", conCHA)

                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateParkingAreaActivity.this, "Update Successfull!!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateParkingAreaActivity.this, ViewParkingAreaActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(CreateParkingAreaActivity.this, "Update Failed !!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateParkingAreaActivity.this, ViewParkingAreaActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Toast.makeText(this, "Please fill necessary fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveToFireStorage(StorageReference strReference,Uri imageUri, String childPath, Bundle bundle){
        strReference.putFile(imageUri).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storage.child(childPath).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                String imageUrl =   task.getResult().toString();

                                // Toast.makeText(CreateParkingAreaActivity.this, imageUrl, Toast.LENGTH_SHORT).show();
                                parkingArea.setParkingAreaImg(imageUrl);

                                if(bundle != null){
                                    // update record to db
                                    updateToFireStore(parkingArea);
                                }else{
                                    // add new record to db
                                    saveToFireStore(parkingArea);
                                }
                            }
                        });

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                prgCreateArea.setProgress(0);
                            }
                        }, 500);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(CreateParkingAreaActivity.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                // load into progress bar
                prgCreateArea.setProgress((int) progress);
                // Toast.makeText(CreateParkingAreaActivity.this, "Uploading...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}