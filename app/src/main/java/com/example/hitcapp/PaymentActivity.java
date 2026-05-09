package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.managers.SessionManager;
import com.example.hitcapp.models.Product;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private EditText etAddress;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etAddress = findViewById(R.id.etAddress);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnConfirmPayment).setOnClickListener(v -> {
            String address = etAddress.getText().toString().trim();
            if (address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                return;
            }
            if (CartManager.getInstance().getCartItems().isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                return;
            }
            processCheckoutSimple();
        });
    }

    private void processCheckoutSimple() {
        List<Product> items = CartManager.getInstance().getCartItems();
        double totalPrice = CartManager.getInstance().getTotalPrice();
        int totalQty = items.size(); // Giả sử mỗi item là 1 đơn vị, hoặc tính tổng qty nếu có trường qty trong cart

        int userId = SessionManager.userId > 0 ? SessionManager.userId : 1;
        String userName = (SessionManager.userName != null && !SessionManager.userName.isEmpty()) 
                ? SessionManager.userName : "user";

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        // Sử dụng phương thức checkout đơn giản mới
        APICaller.getInstance(this).checkoutSimple(userId, userName, totalPrice, totalQty, new APICaller.OnApiResponse() {
            @Override
            public void onSuccess(String message) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(PaymentActivity.this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
                
                CartManager.getInstance().clearCart();
                
                Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(PaymentActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
