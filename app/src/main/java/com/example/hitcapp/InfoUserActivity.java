package com.example.hitcapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.managers.SessionManager;
import com.example.hitcapp.models.User;

public class InfoUserActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPhone, etUsername;
    private ProgressBar progressBar;
    private int userId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infouser);

        userId = SessionManager.userId;

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etUsername = findViewById(R.id.etUsername);
        progressBar = findViewById(R.id.progressBar);

        fetchUserDetails();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnSaveProfile).setOnClickListener(v -> {
            if (currentUser != null) {
                updateUserProfile();
            }
        });
    }

    private void fetchUserDetails() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        APICaller.getInstance(this).getUserById(userId, new APICaller.OnUserLoaded() {
            @Override
            public void onSuccess(User user) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                currentUser = user;
                populateFields();
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(InfoUserActivity.this, "Failed to load user info: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateFields() {
        if (currentUser == null) return;
        if (etFirstName != null) etFirstName.setText(currentUser.getFirstName());
        if (etLastName != null) etLastName.setText(currentUser.getLastName());
        if (etEmail != null) etEmail.setText(currentUser.getEmailId());
        if (etUsername != null) etUsername.setText(currentUser.getUserName());
    }

    private void updateUserProfile() {
        currentUser.setFirstName(etFirstName.getText().toString());
        currentUser.setLastName(etLastName.getText().toString());
        currentUser.setEmailId(etEmail.getText().toString());
        currentUser.setUserName(etUsername.getText().toString());

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        // Fixed to match updated APICaller signature
        APICaller.getInstance(this).updateUser(currentUser, new APICaller.OnApiResponse() {
            @Override
            public void onSuccess(String message) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(InfoUserActivity.this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(InfoUserActivity.this, "Update Failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
