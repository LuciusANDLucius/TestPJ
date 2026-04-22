package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InfoUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infouser);

        // Receive data
        String email = getIntent().getStringExtra("USER_EMAIL");
        boolean isGuest = getIntent().getBooleanExtra("IS_GUEST", false);

        // Bind data to UI
        TextView tvEmail = findViewById(R.id.tvUserEmail); // Need to ensure this ID exists in XML
        TextView tvName = findViewById(R.id.tvUserName);

        if (isGuest) {
            if (tvName != null) tvName.setText("Guest User");
            if (tvEmail != null) tvEmail.setText("Not connected");
        } else {
            if (tvName != null) tvName.setText("Member");
            if (tvEmail != null) tvEmail.setText(email);
        }

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnSaveProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InfoUserActivity.this, "Profile Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnChangePass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGuest) {
                    Toast.makeText(InfoUserActivity.this, "Please login to use this feature", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(InfoUserActivity.this, ChangePasswordActivity.class));
                }
            }
        });
    }
}