package com.example.infocity.shop_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.infocity.R;
import com.example.infocity.admin.AddCity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AddOfferActivity extends AppCompatActivity {

    private ImageView iv_image;
    private EditText title,desc;
    private Button add;
    private StorageReference storageReference;
    private Uri imgUri,resultUri;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        iv_image = findViewById(R.id.iv_image_add_offer);
        title = findViewById(R.id.et_title_add_offer);
        desc = findViewById(R.id.et_description_add_offer);
        add = findViewById(R.id.bt_add_add_offer);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultUri!=null){
                    uploadImage(resultUri);
                }
            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity(imgUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddOfferActivity.this);
            }
        });

    }

    private void uploadImage(Uri resultUri) {
        storageReference = FirebaseStorage.getInstance().getReference("cities/images/" + title.getText().toString() + ".jpg");
        storageReference.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
//                progress = new ProgressDialog(AddOfferActivity.this);
//                progress.show();
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    AddCity.imageUrl = downloadUri.toString();
                    storeData(downloadUri.toString());
                    Log.d("image_operation","image_uploaded");

                } else {
                    Toast.makeText(AddOfferActivity.this, "upload failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                    Log.d("image_operation","image_upload failed");

                }
            }
        });
    }

    private void storeData(String url) {


        Map<String,Object> params = new HashMap<>();
        params.put("offer_name",title.getText().toString());
        params.put("offer_desc",desc.getText().toString());
        params.put("img_uri",url);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users/" + (FirebaseAuth.getInstance().getCurrentUser()).getUid() + "/" + category + "/properties/offers")
                .add(params).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isComplete()){
                    Log.d("image_operation","data_uploaded");
                    Toast.makeText(AddOfferActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("image_operation","data_upload failed");
                    Toast.makeText(AddOfferActivity.this, "Sorry!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                resultUri = result.getUri();
                Picasso.get().load(resultUri).into(iv_image);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

                Toast.makeText(this, "error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }

}
