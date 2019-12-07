package com.example.infocity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Button bt_login,bt_createAc;
    private EditText et_email_login,et_email_signup,et_password_login,et_password_signup;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(LoginActivity.this);
        mAuth = FirebaseAuth.getInstance();
//        SharedPreferences sharedPreferences = getSharedPreferences("role_selector",MODE_PRIVATE);
//        String role = sharedPreferences.getString("role","null");
        if (mAuth.getCurrentUser() != null){
            //goto dashboard
            Toast.makeText(this, "User Already exist", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, SelectCity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        bt_login = findViewById(R.id.bt_login_login);
        bt_createAc = findViewById(R.id.bt_createAc_sheet);
        et_email_login = findViewById(R.id.et_email_login);
        et_password_login = findViewById(R.id.et_password_login);
        et_email_signup = findViewById(R.id.et_email_sheet);
        et_password_signup = findViewById(R.id.et_password_sheet);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(et_email_login.getText().toString(),et_password_login.getText().toString());
            }
        });

        bt_createAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(et_email_signup.getText().toString(),et_password_signup.getText().toString());
            }
        });

    }

    private void signUp(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "User Created Successfuly", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,SelectCity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    //FAILURE
                    Toast.makeText(LoginActivity.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String email,String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //LOGIN SUCCESSFUL
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }else{
                  //ERROR
                    Toast.makeText(LoginActivity.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
