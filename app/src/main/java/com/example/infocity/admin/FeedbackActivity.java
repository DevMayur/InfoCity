package com.example.infocity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.infocity.R;
import com.example.infocity.adapters.FeedbackAdapter;
import com.example.infocity.models.FeedbackModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FeedbackModel> fList;
    private FeedbackAdapter feedbackAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        recyclerView = findViewById(R.id.recycler_view_feedback);
        fList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(fList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(feedbackAdapter);

        retrive();

    }

    private void retrive() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("feedback").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            FeedbackModel model = doc.getDocument().toObject(FeedbackModel.class);
                            fList.add(model);
                            feedbackAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
