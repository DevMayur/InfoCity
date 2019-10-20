package com.example.infocity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.infocity.adapters.CityAdapter;
import com.example.infocity.models.CityModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SelectCity extends AppCompatActivity {

    private ImageButton ibt_addCity;
    private RecyclerView recyclerView;
    private List<CityModel> cList;
    private CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        recyclerView = findViewById(R.id.recyclerView_cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cList = new ArrayList<>();
        adapter = new CityAdapter(this,cList);
        recyclerView.setAdapter(adapter);

        fetchData();



        ibt_addCity = findViewById(R.id.ibt_add_city_select_city);
        ibt_addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCity.this,AddCity.class));
            }
        });

    }

    private void fetchData() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("cities").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentChange documentChange: queryDocumentSnapshots.getDocumentChanges()){
                    CityModel model = new CityModel();
                    model = documentChange.getDocument().toObject(CityModel.class);
                    cList.add(model);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
