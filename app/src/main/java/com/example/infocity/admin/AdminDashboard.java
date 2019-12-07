package com.example.infocity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infocity.R;
import com.example.infocity.RoleSelector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminDashboard extends AppCompatActivity {

    private ImageView iv_verification,iv_rejected,iv_feedback,iv_message,iv_add_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        getPassword();

        initVariables();

        initListners();

    }

    private void getPassword() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.get_password_dialog, null);
        dialogBuilder.setCancelable(false);

        final EditText et_password = dialogView.findViewById(R.id.et_password_dialog);
        final Button login = dialogView.findViewById(R.id.bt_login_password_dialog);
        final Button cancel = dialogView.findViewById(R.id.bt_cancel_get_password_dialog);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, RoleSelector.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_password.getText().toString().equals(getResources().getString(R.string.admin_password))){
                    Toast.makeText(AdminDashboard.this, "Welcome ADMINISTRATOR", Toast.LENGTH_SHORT).show();
                    dialogBuilder.dismiss();
                }else{
                    Toast.makeText(AdminDashboard.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminDashboard.this, RoleSelector.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void initListners() {
        iv_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, EntityVerification.class);
                intent.putExtra("address_tag","verification_queue");
                startActivity(intent);
            }
        });

        iv_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminDashboard.this, EntityVerification.class);
                intent.putExtra("address_tag","rejected_queue");
                startActivity(intent);

            }
        });

        iv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminDashboard.this,FeedbackActivity.class));

            }
        });

        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brodcasteDialog();
            }
        });

        iv_add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, AddCity.class));
            }
        });

    }

    private void initVariables() {
        iv_verification = findViewById(R.id.iv_verification_admin_dashboard);
        iv_rejected = findViewById(R.id.iv_rejected_admin_dashboard);
        iv_feedback = findViewById(R.id.iv_feedback_admin_dashboard);
        iv_message = findViewById(R.id.iv_message_admin_dashboard);
        iv_add_city = findViewById(R.id.iv_add_city);
    }

    private void brodcasteDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.brodcaste_message, null);

        final TextView tv_old_message = dialogView.findViewById(R.id.tv_old_message);
        final EditText et_new_message = dialogView.findViewById(R.id.et_new_message);
        Button cancle = dialogView.findViewById(R.id.bt_discard);
        Button delete_old_message = dialogView.findViewById(R.id.bt_delete_old_message);
        Button update = dialogView.findViewById(R.id.bt_save_message);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("brodcaste_message").document("message").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            if (documentSnapshot.getBoolean("status")){
                                tv_old_message.setText(documentSnapshot.getString("message"));
                            }else{
                                Toast.makeText(AdminDashboard.this, "Status false", Toast.LENGTH_SHORT).show();
                            }

                        }else{

                            Toast.makeText(AdminDashboard.this, "No message broadcasted yet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> params = new HashMap<>();
                params.put("message",et_new_message.getText().toString());
                params.put("status",true);
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("brodcaste_message").document("message")
                        .set(params).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AdminDashboard.this, "Succesfully broadcasted message!", Toast.LENGTH_SHORT).show();
                            dialogBuilder.dismiss();

                        }else{
                            Toast.makeText(AdminDashboard.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            dialogBuilder.dismiss();
                        }
                    }
                });
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        delete_old_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Map<String, Object> params = new HashMap<>();
                params.put("message",et_new_message.getText().toString());
                params.put("status",false);

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("brodcaste_message").document("message")
                        .set(params).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AdminDashboard.this, "Succesfully deleted!", Toast.LENGTH_SHORT).show();
                            dialogBuilder.dismiss();

                        }else{
                            Toast.makeText(AdminDashboard.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
