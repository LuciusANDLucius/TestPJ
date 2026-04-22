package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button guessBtn1 = findViewById(R.id.guest);
        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);


        guessBtn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("USER_EMAIL", "Guest User");
                intent.putExtra("IS_GUEST", true);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                EditText objEmail = findViewById(R.id.email);
                String sEmail = objEmail.getText().toString();

                EditText objPassword = findViewById(R.id.password);
                String sPassword = objPassword.getText().toString();

                    if(sEmail.equals("email@alo.co") && sPassword.equals("1234")) {
                        Intent it = new Intent(MainActivity.this, HomeActivity.class);
                        it.putExtra("USER_EMAIL", sEmail);
                        it.putExtra("IS_GUEST", false);
                        startActivity(it);
                    }
                else {
                        android.widget.Toast.makeText(MainActivity.this, "Invalid credentials!", android.widget.Toast.LENGTH_SHORT).show();
                }

            };
        });
    }
}