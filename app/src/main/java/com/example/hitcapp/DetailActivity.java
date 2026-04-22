package com.example.hitcapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get data from Intent
        String name = getIntent().getStringExtra("product_name");
        String price = getIntent().getStringExtra("product_price");
        int image = getIntent().getIntExtra("product_image", R.drawable.icon_hitc);

        // Bind data
        TextView tvName = findViewById(R.id.tvProductName);
        TextView tvPrice = findViewById(R.id.tvProductPrice);
        ImageView img = findViewById(R.id.imgProduct);

        if (name != null) {
            tvName.setText(name);
            tvPrice.setText(price);
            img.setImageResource(image);
        }

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnAddToCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add to CartManager
                Product product = new Product(name, price, image);
                CartManager.getInstance().addProduct(product);
                
                Toast.makeText(DetailActivity.this, "Added " + name + " to cart!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}