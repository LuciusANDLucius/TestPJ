package com.example.hitcapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.models.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.tvBackToLogin).setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> {
            performRegistration();
        });
    }

    private void performRegistration() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUserName(username);
        newUser.setEmailId(email);
        newUser.setPassword(password);
        newUser.setActive(true);

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        btnRegister.setEnabled(false);

        APICaller.getInstance(this).register(newUser, new APICaller.OnApiResponse() {
            @Override
            public void onSuccess(String message) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                btnRegister.setEnabled(true);
                Toast.makeText(RegisterActivity.this, "Registration Failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
