package com.example.infocity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class EntityDetails extends AppCompatActivity {

    //API KEY : AIzaSyBwXnsk9nLi2G7xL-xY5tTkyF5_eQXEaWk

    /*app details
    *
    *           SHA1: 1B:BD:13:79:F9:F8:B6:3C:E3:C9:07:75:1F:1A:32:1B:46:8C:0D:EB
                SHA256: 88:E4:86:1C:65:59:E1:56:66:1B:0E:4F:D6:78:60:2F:F9:F0:0C:5A:51:E7:42:7D:09:CA:0C:9F:2B:F3:B0:F0
                Signature algorithm name: SHA1withRSA
                Subject Public Key Algorithm: 2048-bit RSA key
    *
    * */

    private ImageView iv_navigate,iv_view_offers,iv_display_image;
    private TextView tv_address,tv_phone,tv_name;
    private String name,address,phone,img_uri;
    private double longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_details);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");
        img_uri = intent.getStringExtra("img_uri");
        longitude = intent.getDoubleExtra("longitude",0);
        latitude = intent.getDoubleExtra("latitude",0);
        Log.d("co-ordinates_myTag","Longitude : " + String.valueOf(longitude) + " \n" + "Latitude : " + String.valueOf(latitude) );



        initVariables();

        initListeners();

        tv_name.setText(name);
        tv_phone.setText(phone);
        tv_address.setText(address);
        Picasso.get().load(img_uri).into(iv_display_image);

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
        iv_navigate = findViewById(R.id.iv_navigate_entity_details);
        iv_view_offers = findViewById(R.id.iv_view_offers_entity_details);
        iv_display_image = findViewById(R.id.iv_display_image_entity_details);
        tv_address = findViewById(R.id.tv_address_entity_details);
        tv_phone = findViewById(R.id.tv_phone_no_entity_details);
        tv_name = findViewById(R.id.tv_name_entity_details);
    }
}
