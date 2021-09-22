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

    private Uri imgParkingUri;

    private ProgressBar prgCreateArea;

    // DB Declaration
    private FirebaseFirestore db;

    // Storage-Reference Declaration
    private StorageReference storage;

    // ParkingArea Object instantiation
    private ParkingArea parkingArea;

    private ActivityResultLauncher<Intent> fileSelectorLauncher;

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

        // ProgressBar
        prgCreateArea = findViewById(R.id.prgCreateArea);

        // Database initialization
        db = FirebaseFirestore.getInstance();

        // storage initialization
        storage = FirebaseStorage.getInstance().getReference();

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

                String id = UUID.randomUUID().toString();

                String title = edtTitle.getText().toString();
                String address = edtAddress.getText().toString();

                int countBikeSlots = Integer.parseInt(edtCountBikeSlots.getText().toString());
                int countCarSlots = Integer.parseInt(edtCountCarSlots.getText().toString());

                double feeBike = Double.parseDouble(edtFeeBike.getText().toString());
                double feeCar = Double.parseDouble(edtFeeCar.getText().toString());

                int typeBike;
                int typeCar;

                if(chkTypeBike.isChecked()){
                    typeBike = 1;
                }else {
                    typeBike = 0;
                }

                if(chkTypeCar.isChecked()){
                    typeCar = 1;
                }else {
                    typeCar = 0;
                }

                int wattMin;
                int wattMed;
                int wattMax;

                if(chkWattMin.isChecked()){
                    wattMin = 1;
                }else {
                    wattMin = 0;
                }

                if(chkWattMed.isChecked()){
                    wattMed = 1;
                }else {
                    wattMed = 0;
                }

                if(chkWattMax.isChecked()){
                    wattMax = 1;
                }else {
                    wattMax = 0;
                }


                int conTypeTwo;
                int conCombo;
                int conCHA;

                if(chkConTypeTwo.isChecked()){
                    conTypeTwo = 1;
                }else {
                    conTypeTwo = 0;
                }

                if(chkConCombo.isChecked()){
                    conCombo = 1;
                }else {
                    conCombo = 0;
                }

                if(chkConCHA.isChecked()){
                    conCHA = 1;
                }else {
                    conCHA = 0;
                }

                int evFacility;

                if (radEVYes.isChecked()){
                    evFacility = 1;
                }else{
                    evFacility = 0;
                }

                parkingArea = new ParkingArea(id, title, address, typeBike, typeCar,
                        countBikeSlots, countCarSlots, feeBike, feeCar, evFacility,
                        wattMin, wattMed, wattMax, conTypeTwo, conCombo, conCHA);

                if(imgParkingUri != null){
                    String childPath = System.currentTimeMillis() + "." + getFileExtension(imgParkingUri);
                    StorageReference fileReference = storage.child(childPath);

                    saveToFireStorage(fileReference, imgParkingUri, childPath);
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
                      public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
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

    public void saveToFireStorage(StorageReference strReference,Uri imageUri, String childPath){
        strReference.putFile(imageUri).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storage.child(childPath).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                String imageUrl =   task.getResult().toString();

                                Toast.makeText(CreateParkingAreaActivity.this, imageUrl, Toast.LENGTH_SHORT).show();
                                parkingArea.setParkingAreaImg(imageUrl);

                                Log.i("imgURL", "TEST");
                                Log.i("imgURL", imageUrl);

                                // add new record to db
                                saveToFireStore(parkingArea);
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