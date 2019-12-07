package com.example.infocity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.infocity.admin.AdminDashboard;
import com.example.infocity.shop_owner.OwnerDashboard;

public class RoleSelector extends AppCompatActivity {

    private Button admin,shop,user;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selector);

        SharedPreferences sharedPreferences = getSharedPreferences("role_selector",MODE_PRIVATE);
        String status = sharedPreferences.getString("status","null");
        if (status.equals("selected")){
            String role = sharedPreferences.getString("role","null");
            switch (role){
                case "admin":
                    startActivity(new Intent(RoleSelector.this,AdminDashboard.class));
                    finish();
                    break;
                case "shop_owner":
                    startActivity(new Intent(RoleSelector.this, OwnerDashboard.class));
                    finish();
                    break;
                case "user":
                    startActivity(new Intent(RoleSelector.this,LoginActivity.class));
                    finish();
                    break;
            }
        }

        admin = findViewById(R.id.bt_admin_role_selector);
        shop = findViewById(R.id.bt_shop_owner_role_seelector);
        user = findViewById(R.id.bt_user_role_selector);
        SharedPreferences sharedPreferences1 = getSharedPreferences("role_selector",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences1.edit();

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("role","admin");
                editor.commit();
                startActivity(new Intent(RoleSelector.this,AdminDashboard.class));
                finish();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("role","shop_owner");
                editor.putString("status","selected");
                editor.commit();
                startActivity(new Intent(RoleSelector.this,LoginActivity.class));
                finish();
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("role","user");
                editor.putString("status","selected");
                editor.commit();
                startActivity(new Intent(RoleSelector.this,LoginActivity.class));
                finish();
            }
        });

    }
}
