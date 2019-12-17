package com.example.infocity.shop_owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.infocity.R;
import com.example.infocity.SelectCity;
import com.example.infocity.adapters.SingleEntityAdapter;
import com.example.infocity.models.SingleEntityModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OwnerDashboard extends AppCompatActivity {
    private List<SingleEntityModel> eList;
    private SingleEntityAdapter singleEntityAdapter;
    private List<String> cList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);

        eList = new ArrayList<>();
        cList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_activity_owner);
        Button bt_add_shop = findViewById(R.id.bt_add_shop_owner_dashbord);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        singleEntityAdapter = new SingleEntityAdapter(this,eList,cList);
        recyclerView.setAdapter(singleEntityAdapter);

        bt_add_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerDashboard.this, SelectCity.class));
            }
        });

        List<String> categories = new ArrayList<>();
        categories.add("hospital");
        categories.add("hotel");
        categories.add("shop");
        categories.add("medical");
        categories.add("atm");
        categories.add("store");
        categories.add("petrol_pump");
        categories.add("police_station");
        categories.add("school");
        categories.add("bus_stand");

        fetchData(categories);
    }

    private void fetchData(List<String> categories){
        for (final String category : categories){
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("users/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "/" + category)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()){
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            SingleEntityModel model = doc.getDocument().toObject(SingleEntityModel.class);
                            eList.add(model);
                            cList.add(category);
                            singleEntityAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Log.d("tag","document snapshot empty");
                    }
                }
            });
        }
    }

}
