package com.example.teammark;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class Admin_PostAD extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    Button takePhotoBtn, mpostAD, mshowAD, btnPromote, btnUploadPic;

    EditText mtitle, mdistrict, mengine, mmobile, mland, mamount, mdesc;
    Spinner mperson, mfuel;

    private FirebaseFirestore db;

    private String uTitle, uDistrict, uPerson, uAmount, uLand, uMobile, uEngine, uDesc, uFuel, uId;

    Dialog dialog;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_ad);

        imageView = (ImageView) findViewById(R.id.ImgPostAd);
        takePhotoBtn = (Button) findViewById(R.id.btnTakePhoto);
        mtitle = (EditText) findViewById(R.id.editTxt1);
        mdistrict = (EditText) findViewById(R.id.editTxt2);
        mperson = (Spinner) findViewById(R.id.editTxt3);
        mengine = (EditText) findViewById(R.id.editTxt4);
        mfuel = (Spinner) findViewById(R.id.editTxt5);
        mmobile = (EditText) findViewById(R.id.editTxt6);
        mland = (EditText) findViewById(R.id.editTxt7);
        mamount = (EditText) findViewById(R.id.editTxt8);
        mdesc = (EditText) findViewById(R.id.editTxt10);
        mpostAD = (Button) findViewById(R.id.btnPostAD);
        mshowAD = (Button) findViewById(R.id.btnShow);
        btnPromote = (Button) findViewById(R.id.btnPromote);
        btnUploadPic = (Button) findViewById(R.id.btnUpdatePhoto);

        db = FirebaseFirestore.getInstance();

        dialog = new Dialog(Admin_PostAD.this);
        dialog.setContentView(R.layout.custom_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button okay = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Admin_PostAD.this, "Okay", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Admin_PostAD.this, "cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mpostAD.setText("Update AD");
            uTitle = bundle.getString("uTitle");
            uId = bundle.getString("uId");
            uDistrict = bundle.getString("uDistrict");
            uPerson = bundle.getString("uPerson");
            uAmount = bundle.getString("uAmount");
            uMobile = bundle.getString("uMobile");
            uLand = bundle.getString("uLand");
            uDesc = bundle.getString("uDesc");
            uFuel = bundle.getString("uFuel");
            uEngine = bundle.getString("uEngine");
            mtitle.setText(uTitle);
            mamount.setText(uAmount);
            mdistrict.setText(uAmount);
            mmobile.setText(uMobile);
            mland.setText(uLand);
            mdesc.setText(uDesc);
            mengine.setText(uEngine);
            //mfuel.setAdapter(uFuel);

        } else {
            mpostAD.setText("Post AD");
        }

        mshowAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_PostAD.this, ShowActivity.class));
            }
        });

        btnPromote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_PostAD.this, PromoteAD2.class));
            }
        });

        mpostAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mtitle.getText().toString();
                String district = mdistrict.getText().toString();
                String person = mperson.getSelectedItem().toString();
                String engine = mengine.getText().toString();
                String fuel = mfuel.getSelectedItem().toString();
                String mobile = mmobile.getText().toString();
                String land = mland.getText().toString();
                String amount = mamount.getText().toString();
                String desc = mdesc.getText().toString();

                Bundle bundel1 = getIntent().getExtras();
                if (bundel1 != null) {
                    String id = uId;
                    updateToFireStore(id, title, district, person, engine, fuel, mobile, land, amount, desc);
                } else {
                    String id = UUID.randomUUID().toString();

                    saveToFireStore(id, title, district, person, engine, fuel, mobile, land, amount, desc);
                }

            }
        });
        //onclick listner for custom dialog
//        mpostAD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//            }
//        });


    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }

    }

    private void updateToFireStore(String id, String title, String district, String person, String engine, String fuel, String mobile, String land, String amount, String desc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want Update the changes?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("Advertisements").document(id).update("title", title, "district", district, "person", person, "engine", engine, "fuel", fuel, "mobile", mobile, "land", land, "amount", amount, "desc", desc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Admin_PostAD.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Admin_PostAD.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Admin_PostAD.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AlertDialog editAlert = builder.create();
        editAlert.show();

    }

    private void saveToFireStore(String id, String title, String district, String person, String engine, String fuel, String mobile, String land, String amount, String desc) {

        if (title.isEmpty()) {
            mtitle.setError("Title is required");
            mtitle.requestFocus();
            return;
        }

        if (district.isEmpty()) {
            mdistrict.setError("District is required");
            mdistrict.requestFocus();
            return;
        }

        if (mobile.isEmpty()) {
            mmobile.setError("Mobile Number is Required");
            mmobile.requestFocus();
            return;
        }

        if (mobile.length() < 10 || mobile.length() > 10) {
            mmobile.setError("Enter a valid mobile number");
            mmobile.requestFocus();
            return;
        }

        if (!title.isEmpty() && !fuel.isEmpty() && !mobile.isEmpty() && !amount.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("title", title);
            map.put("district", district);
            map.put("person", person);
            map.put("engine", engine);
            map.put("Fuel", fuel);
            map.put("mobile", mobile);
            map.put("land", land);
            map.put("amount", amount);
            map.put("desc", desc);

            db.collection("Advertisements").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dialog.show();
                                Toast.makeText(Admin_PostAD.this, "Data added successfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Admin_PostAD.this, "Faild to save", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Empty Fields not allowed", Toast.LENGTH_SHORT).show();
        }
    }
}