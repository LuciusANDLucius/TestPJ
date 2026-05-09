package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hitcapp.managers.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister, btnGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);
        btnGuest = findViewById(R.id.guest);

        btnLogin.setOnClickListener(v -> loginUser());
        btnRegister.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));
        btnGuest.setOnClickListener(v -> {
            SessionManager.clear();
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.180.6.181:8080/user/login";

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("emailId", email);
            loginData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, loginData,
                response -> {
                    try {
                        SessionManager.userId = response.getInt("id");
                        SessionManager.email = response.getString("emailId");
                        SessionManager.password = password;
                        SessionManager.userName = response.optString("userName", "User");

                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, "Error parsing server data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }
}
