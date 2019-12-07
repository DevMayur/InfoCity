package com.example.infocity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.infocity.R;
import com.example.infocity.SelectCity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class AddCity extends AppCompatActivity {

    private ImageButton ibt_confirm,ibt_add_image;
    private ImageView iv_placeholder;
    private EditText et_city_name;
    private Uri imgUri,resultUri;
    private StorageReference storageReference;
    private ProgressDialog progress;

    public static String imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        ibt_confirm = findViewById(R.id.ibt_confirm_add_city);
        ibt_add_image = findViewById(R.id.ibt_select_image_add_city);
        iv_placeholder = findViewById(R.id.iv_placeholder_add_city);
        et_city_name = findViewById(R.id.et_city_name_add_city);

        ibt_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity(imgUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddCity.this);
            }
        });

        ibt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_city_name.getText())){
                    if(resultUri == null){
                        Toast.makeText(AddCity.this, "Please Select an image", Toast.LENGTH_SHORT).show();
                    }else{
                        uploadImage(resultUri);
                    }
                }else{
                    Toast.makeText(AddCity.this, "Enter Valid City Name", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void uploadImage(Uri resultUri) {
        storageReference = FirebaseStorage.getInstance().getReference("cities/images/" + et_city_name.getText().toString() + ".jpg");
        storageReference.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                progress = new ProgressDialog(AddCity.this);
                progress.show();
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    AddCity.imageUrl = downloadUri.toString();
                    storeData(downloadUri.toString());
                    progress.dismiss();
                } else {
                    Toast.makeText(AddCity.this, "upload failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }

    private void storeData(String url) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> params = new HashMap<>();
        params.put("name",et_city_name.getText().toString());
        params.put("img_uri",url);
        firebaseFirestore.collection("cities").document(et_city_name.getText().toString()).set(params).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //SUCCESS

                    Toast.makeText(AddCity.this, "City Succesfully Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCity.this, SelectCity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else{
                    //FAILURE

                    Toast.makeText(AddCity.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();

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
                Picasso.get().load(resultUri).into(iv_placeholder);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

                Toast.makeText(this, "error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }
}
