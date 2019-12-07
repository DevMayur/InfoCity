package com.example.infocity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.infocity.adapters.CityAdapter;
import com.example.infocity.admin.AddCity;
import com.example.infocity.models.CityModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCity extends AppCompatActivity {

    private Button ibt_addCity;
    private RecyclerView recyclerView;
    private List<CityModel> cList;
    private CityAdapter adapter;
    private Button bt_feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        recyclerView = findViewById(R.id.recyclerView_cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cList = new ArrayList<>();
        adapter = new CityAdapter(this,cList);
        recyclerView.setAdapter(adapter);
        bt_feedback = findViewById(R.id.bt_feedback_select_city);

        bt_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackDialogBuild();
            }
        });

        fetchData();



        ibt_addCity = findViewById(R.id.ibt_add_city_select_city);
        ibt_addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCity.this, AddCity.class));
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

    private void feedbackDialogBuild() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        final EditText et_feedback =  dialogView.findViewById(R.id.et_feedback_dialog);
        final EditText et_email = dialogView.findViewById(R.id.et_email_dialog);
        Button bt_cancel_feedback =  dialogView.findViewById(R.id.bt_cancel_dialog);
        Button bt_submit_feedback =  dialogView.findViewById(R.id.bt_submit_dialog);
        final ConstraintLayout coordinatorLayout = findViewById(R.id.constraint_layout_select_city);

        bt_cancel_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        bt_submit_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                Map<String,Object> params = new HashMap<>();
                params.put("email",et_email.getText().toString());
                params.put("feedback",et_feedback.getText().toString());

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("feedback").add(params)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            Snackbar.make(coordinatorLayout,"Thank you for your feedback !" , Snackbar.LENGTH_SHORT).show();
                            dialogBuilder.dismiss();

                        }else{
                            Snackbar.make(coordinatorLayout,"Error" + task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                            dialogBuilder.dismiss();
                        }
                    }
                });

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

}
