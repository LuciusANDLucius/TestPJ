package com.example.hitcapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.managers.SessionManager;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etOldPassword, etNewPassword, etConfirmNewPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnUpdatePassword).setOnClickListener(v -> {
            updatePassword();
        });
    }

    private void updatePassword() {
        String oldPass = etOldPassword.getText().toString().trim();
        String newPass = etNewPassword.getText().toString().trim();
        String confirmPass = etConfirmNewPassword.getText().toString().trim();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        APICaller.getInstance(this).changePassword(SessionManager.userId, oldPass, newPass, new APICaller.OnApiResponse() {
            @Override
            public void onSuccess(String message) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                SessionManager.password = newPass;
                finish();
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(ChangePasswordActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
