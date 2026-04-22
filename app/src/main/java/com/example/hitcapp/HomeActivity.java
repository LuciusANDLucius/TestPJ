package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        boolean isGuest = getIntent().getBooleanExtra("IS_GUEST", false);


        Button backtomain = findViewById(R.id.Backtomain);
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.navSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });

        findViewById(R.id.cardSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });


        findViewById(R.id.navCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });


        findViewById(R.id.navProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InfoUserActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                intent.putExtra("IS_GUEST", isGuest);
                startActivity(intent);
            }
        });


        findViewById(R.id.navSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, Setting.class));
            }
        });


        ListView lvProducts = findViewById(R.id.lvProducts);
        List<Product> productList = new ArrayList<>();
        
        productList.add(new Product("iPhone 15 Pro", "$999", R.drawable.icon_hitc));
        productList.add(new Product("Samsung S24 Ultra", "$1199", R.drawable.icon_hitc));
        productList.add(new Product("MacBook Air M3", "$1299", R.drawable.icon_hitc));
        productList.add(new Product("Sony WH-1000XM5", "$349", R.drawable.icon_hitc));
        productList.add(new Product("iPad Pro 12.9", "$1099", R.drawable.icon_hitc));
        productList.add(new Product("Apple Watch Series 9", "$399", R.drawable.icon_hitc));
        productList.add(new Product("Nintendo Switch OLED", "$349", R.drawable.icon_hitc));
        productList.add(new Product("Dell XPS 15", "$1599", R.drawable.icon_hitc));
        productList.add(new Product("Logitech MX Master 3S", "$99", R.drawable.icon_hitc));
        productList.add(new Product("Keychron K2 V2", "$79", R.drawable.icon_hitc));

        ProductAdapter adapter = new ProductAdapter(this, productList);
        lvProducts.setAdapter(adapter);

        // Click item to see detail
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position);
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("product_name", selectedProduct.getName());
                intent.putExtra("product_price", selectedProduct.getPrice());
                intent.putExtra("product_image", selectedProduct.getImageResource());
                startActivity(intent);
            }
        });
    }
}