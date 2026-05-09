package com.example.hitcapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.models.Product;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private int productId;
    private Product currentProduct;
    private ProgressBar progressBar;
    private TextView tvName, tvPrice, tvDescription;
    private ImageView imgProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        tvName = findViewById(R.id.tvProductName);
        tvPrice = findViewById(R.id.tvProductPrice);
        tvDescription = findViewById(R.id.tvProductDescription);
        imgProduct = findViewById(R.id.imgProduct);
        progressBar = findViewById(R.id.progressBar);

        if (productId != -1) {
            fetchProductDetails();
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnAddToCart).setOnClickListener(v -> {
            if (currentProduct != null) {
                addToCart();
            }
        });
    }

    private void fetchProductDetails() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        APICaller.getInstance(this).getProductById(productId, new APICaller.OnProductLoaded() {
            @Override
            public void onLoaded(List<Product> productList) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (productList != null && !productList.isEmpty()) {
                    currentProduct = productList.get(0);
                    displayProduct();
                } else {
                    Toast.makeText(DetailActivity.this, "Không tìm thấy thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayProduct() {
        if (currentProduct == null) return;
        tvName.setText(currentProduct.getName());
        tvPrice.setText(getString(R.string.price_format, currentProduct.getPrice()));
        if (tvDescription != null) tvDescription.setText(currentProduct.getDescription());

        // Default image
        imgProduct.setImageResource(R.drawable.icon_hitc);
        
        String base64Image = currentProduct.getImageBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                if (decodedByte != null) {
                    imgProduct.setImageBitmap(decodedByte);
                }
            } catch (Exception e) {
                // Keep default
            }
        }
    }

    private void addToCart() {
        CartManager.getInstance().addProduct(currentProduct);
        Toast.makeText(DetailActivity.this, "Added to cart successfully!", Toast.LENGTH_SHORT).show();
    }
}
