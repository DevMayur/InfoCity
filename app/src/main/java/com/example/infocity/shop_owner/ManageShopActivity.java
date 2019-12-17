package com.example.infocity.shop_owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infocity.R;
import com.example.infocity.adapters.OfferAdapter;
import com.example.infocity.models.OfferModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageShopActivity extends AppCompatActivity {

    private Button add_offer;
    private RecyclerView recyclerView;
    private OfferAdapter offerAdapter;
    private List<OfferModel> oList;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shop);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        add_offer = findViewById(R.id.bt_add_offer_manage_shop);
        recyclerView = findViewById(R.id.recycler_view_manage_shop);
        oList = new ArrayList<>();
        offerAdapter = new OfferAdapter(this,oList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(offerAdapter);

        add_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ManageShopActivity.this,AddOfferActivity.class);
                intent1.putExtra("category",category);
                startActivity(intent1);
            }
        });

        fetchOffers();

    }


    private void fetchOffers() {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "/" + category +"/properties/offers")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            OfferModel model;
                            model = doc.getDocument().toObject(OfferModel.class);
                            oList.add(model);
                            offerAdapter.notifyDataSetChanged();
                        }

                    }
                });


    }
}
