package com.example.infocity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Categories extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_hospital,iv_hotel,iv_shop,iv_medical,iv_atm,iv_stores,iv_petrol_pumps,iv_police_station,iv_school,iv_bus_stand;
    private String category = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        initVariables();

        initListners();

    }

    private void initListners() {
        iv_hospital.setOnClickListener(this);
        iv_hotel.setOnClickListener(this);
        iv_shop.setOnClickListener(this);
        iv_medical.setOnClickListener(this);
        iv_atm.setOnClickListener(this);
        iv_stores.setOnClickListener(this);
        iv_petrol_pumps.setOnClickListener(this);
        iv_police_station.setOnClickListener(this);
        iv_school.setOnClickListener(this);
        iv_bus_stand.setOnClickListener(this);
    }

    private void initVariables() {
        iv_hospital = findViewById(R.id.hospital);
        iv_hotel = findViewById(R.id.hotel);
        iv_shop = findViewById(R.id.shop);
        iv_medical = findViewById(R.id.medical);
        iv_atm = findViewById(R.id.atm);
        iv_stores = findViewById(R.id.store);
        iv_petrol_pumps = findViewById(R.id.petrol_pump);
        iv_police_station = findViewById(R.id.police_station);
        iv_school = findViewById(R.id.school);
        iv_bus_stand = findViewById(R.id.bus_stand);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.hospital:
                category = "hospital";
                break;

            case R.id.hotel:
                category = "hotel";

                break;

            case R.id.shop:
                category = "shop";

                break;

            case R.id.medical:
                category = "medical";

                break;

            case R.id.atm:
                category = "atm";

                break;

            case R.id.store:
                category = "store";

                break;

            case R.id.petrol_pump:
                category = "petrol_pump";

                break;

            case R.id.police_station:
                category = "police_station";

                break;

            case R.id.school:
                category = "school";

                break;

            case R.id.bus_stand:
                category = "bus_stand";

                break;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("category",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("category",category);
        editor.commit();
        Intent intent = new Intent(Categories.this,ShowEntities.class);
        intent.putExtra("category",category);
        startActivity(intent);

    }
}
