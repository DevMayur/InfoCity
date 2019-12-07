package com.example.infocity.shop_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.infocity.admin.AddCity;
import com.example.infocity.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddEntity extends AppCompatActivity implements View.OnClickListener {

    private ImageView displayImage, iv_get_location;
    private ImageButton ibt_add_image, ibt_confirm;
    private EditText et_shop_name, et_address, et_other_information, et_phone_number,et_gst;
    private Uri imgUri, resultUri;
    public double global_longitude, global_lattitude;
    private String category;
    private ProgressDialog progress;
    public static final String isVerified = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entity);

        //initially isVerified is default set to true
        //after seting up verfication method set isVerfied accordingly

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        initVariables();

        initListeners();

        getLocation();

    }

    private void initListeners() {
        displayImage.setOnClickListener(this);
        ibt_add_image.setOnClickListener(this);
        ibt_confirm.setOnClickListener(this);
    }

    private void initVariables() {
        displayImage = findViewById(R.id.iv_placeholder_add_entity);
        iv_get_location = findViewById(R.id.iv_get_location_add_entity);
        ibt_add_image = findViewById(R.id.ibt_add_edit_entity);
        ibt_confirm = findViewById(R.id.ibt_confirm_add_entity);
        et_shop_name = findViewById(R.id.et_name_add_entity);
        et_address = findViewById(R.id.et_address_add_entity);
        et_other_information = findViewById(R.id.et_other_info_add_entity);
        et_phone_number = findViewById(R.id.et_phone_add_entity);
        et_gst = findViewById(R.id.et_gst_edit_entity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_placeholder_add_entity:

                //add Image
                getImage();

                break;

            case R.id.ibt_add_edit_entity:

                //add Image
                getImage();

                break;

            case R.id.ibt_confirm_add_entity:

                //confirm
                if (resultUri != null) {
                    if (et_gst.getText().toString().length() == 15) {
                        if (!TextUtils.isEmpty(et_shop_name.getText())
                                && !TextUtils.isEmpty(et_phone_number.getText()) && !TextUtils.isEmpty(et_address.getText())) {

                            uploadImage(resultUri);

                        } else {
                            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Please Provide a valid GST number!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Please add image", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void addEntity(String uri) {

        //get values from fields
        //get location co-ordinates from maps API
        //save MAP co-ordinates

        Map<String, Object> params = new HashMap<>();

        params.put("name", et_shop_name.getText().toString());
        params.put("address", et_address.getText().toString());
        params.put("phone", et_phone_number.getText().toString());
        params.put("other", et_other_information.getText().toString());
        params.put("longitude", global_longitude);
        params.put("latitude", global_lattitude);
        params.put("isVerified",isVerified);
        params.put("img_uri", uri);
        params.put("gst_no",et_gst.getText().toString());
        SharedPreferences sharedPreferences = getSharedPreferences("selected_city", Context.MODE_PRIVATE);
        final String city = sharedPreferences.getString("city", "null");
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        //for seller

        firebaseFirestore.collection("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + category).add(params)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            String id = task.getResult().getId();
                            final Map<String, Object> shopID = new HashMap<>();
                            shopID.put("category", category);
                            shopID.put("shop_id", id);
                            shopID.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            firebaseFirestore.collection("cities/" + city + "/" + category).add(shopID)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {

                                                firebaseFirestore.collection("verification_queue").add(shopID).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()){
                                                            AddEntity.super.onBackPressed();
                                                        }else{
                                                            Toast.makeText(AddEntity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                                            } else {
                                                Toast.makeText(AddEntity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(AddEntity.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void getImage() {
        CropImage.activity(imgUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(AddEntity.this);
    }


    private void uploadImage(Uri resultUri) {
        final StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference("shops/images/" + et_shop_name.getText().toString() + ".jpg");
        storageReference.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                progress = new ProgressDialog(AddEntity.this);
                progress.show();
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    AddCity.imageUrl = downloadUri.toString();
                    addEntity(downloadUri.toString());
                    progress.dismiss();
                } else {
                    Toast.makeText(AddEntity.this, "upload failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }

    private void getLocation() {
        //set address field from gps

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                resultUri = result.getUri();
                Picasso.get().load(resultUri).into(displayImage);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

                Toast.makeText(this, "error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }



    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
//            final Double longitude = loc.getLongitude();
//            final Double latitude = loc.getLatitude();

            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, false));
            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getAddressLine(0)  +" , " +addresses.get(0).getAddressLine(1)  +" , " +addresses.get(0).getAddressLine(2);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            final String s = cityName;
            iv_get_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_address.setText(s);
                    global_longitude = longitude;
                    global_lattitude = latitude;
                }
            });
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }




}


