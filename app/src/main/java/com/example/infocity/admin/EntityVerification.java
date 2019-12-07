package com.example.infocity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.infocity.R;
import com.example.infocity.adapters.ShopRejectedAdapter;
import com.example.infocity.adapters.ShopVerificationAdapter;
import com.example.infocity.models.ShopReferenceModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EntityVerification extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShopVerificationAdapter shopReferenceAdapter;
    private List<ShopReferenceModel> rList;
    private String collection_address;
    private ShopRejectedAdapter shopRejectedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_verification);

        Intent intent = getIntent();
        collection_address = intent.getStringExtra("address_tag");

        recyclerView = findViewById(R.id.recycler_view_verification);
        rList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopReferenceAdapter = new ShopVerificationAdapter(this,rList);
        shopRejectedAdapter = new ShopRejectedAdapter(this,rList);
        if (collection_address.equals("rejected_queue")){

            recyclerView.setAdapter(shopRejectedAdapter);

        }else{

            recyclerView.setAdapter(shopReferenceAdapter);
        }

        retrive();

    }

    private void retrive() {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(collection_address).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            ShopReferenceModel model = doc.getDocument().toObject(ShopReferenceModel.class).withId(doc.getDocument().getId());
                            rList.add(model);
                            if (collection_address.equals("rejected_queue")){

                                shopRejectedAdapter.notifyDataSetChanged();

                            }else{

                                shopReferenceAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

}
