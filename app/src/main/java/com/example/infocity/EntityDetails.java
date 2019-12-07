package com.example.infocity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class EntityDetails extends AppCompatActivity {

    private ImageView iv_navigate,iv_view_offers,iv_display_image;
    private TextView tv_address,tv_phone,tv_name;
    private String name,address,phone,img_uri,shop_id,seller_id;
    private double longitude,latitude;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_details);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");
        img_uri = intent.getStringExtra("img_uri");
        shop_id = intent.getStringExtra("shop_id");
        seller_id = intent.getStringExtra("seller_id");
        longitude = intent.getDoubleExtra("longitude",0);
        latitude = intent.getDoubleExtra("latitude",0);

        Log.d("co-ordinates_myTag","Longitude : " + String.valueOf(longitude) + " \n" + "Latitude : " + String.valueOf(latitude) );

        initVariables();

        initListeners();

        rateStore();

        tv_name.setText(name);
        tv_phone.setText(phone);
        tv_address.setText(address);
        Picasso.get().load(img_uri).into(iv_display_image);

    }



    private void rateStore() {
        int rating = ratingBar.getNumStars();


    }

    private void initListeners() {
        iv_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send longitude and latitude to navigate activity
                Intent intent = new Intent(EntityDetails.this,MapsActivity.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });


        iv_view_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start offers activity
            }
        });

        iv_display_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show shop display image




            }
        });

        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make a phone using intent
            }
        });
    }

    private void initVariables() {
        ratingBar = findViewById(R.id.ratingBar);
        iv_navigate = findViewById(R.id.iv_navigate_entity_details);
        iv_view_offers = findViewById(R.id.iv_view_offers_entity_details);
        iv_display_image = findViewById(R.id.iv_display_image_entity_details);
        tv_address = findViewById(R.id.tv_address_entity_details);
        tv_phone = findViewById(R.id.tv_phone_no_entity_details);
        tv_name = findViewById(R.id.tv_name_entity_details);
    }
}
