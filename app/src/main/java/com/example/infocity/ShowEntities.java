package com.example.infocity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.infocity.adapters.ShopReferenceAdapter;
import com.example.infocity.models.ShopReferenceModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowEntities extends AppCompatActivity {

    private ImageButton ibt_add_entity;
    private RecyclerView recyclerView;
    private ShopReferenceAdapter shopReferenceAdapter;
    private List<ShopReferenceModel> rList;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_entities);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        final Intent intent1 = new Intent(ShowEntities.this,AddEntity.class);
        intent1.putExtra("category",category);

        ibt_add_entity = findViewById(R.id.ibt_add_entity);

        recyclerView = findViewById(R.id.recyclerView_entities);
        rList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopReferenceAdapter = new ShopReferenceAdapter(this,rList);
        recyclerView.setAdapter(shopReferenceAdapter);


        ibt_add_entity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

        retrive();


    }

    private void retrive() {
        SharedPreferences sharedPreferences = getSharedPreferences("selected_city", Context.MODE_PRIVATE);
        final String city = sharedPreferences.getString("city","null");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("cities/"+city+"/" + category).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            ShopReferenceModel model = doc.getDocument().toObject(ShopReferenceModel.class);
                            rList.add(model);
                            shopReferenceAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
