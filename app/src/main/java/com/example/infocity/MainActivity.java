package com.example.infocity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infocity.admin.AdminDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("brodcaste_message").document("message").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            if (documentSnapshot.getBoolean("status")){
                                setMessage(documentSnapshot.getString("message"));
                            }else{
                                startActivity(new Intent(MainActivity.this, RoleSelector.class));
                                finish();
                            }
                        }else{
                            startActivity(new Intent(MainActivity.this, RoleSelector.class));
                            finish();
                        }
                    }
                });


    }


    private void setMessage(String message) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.message_view, null);

        final TextView tv_messageView = dialogView.findViewById(R.id.tv_message_view);
        final Button bt_Ok = dialogView.findViewById(R.id.bt_ok_message_view);

        tv_messageView.setText(message);
        bt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                        startActivity(new Intent(MainActivity.this, RoleSelector.class));
                        finish();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

}
