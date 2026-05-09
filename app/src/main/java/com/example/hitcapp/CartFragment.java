package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hitcapp.adapters.ProductRecyclerAdapter;
import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.models.Product;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private TextView tvTotal;
    private ProgressBar progressBar;
    private ProductRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCart = view.findViewById(R.id.lvCart); // Mapping the ID from layout
        tvTotal = view.findViewById(R.id.tvTotal);
        progressBar = view.findViewById(R.id.progressBar);

        List<Product> cartItems = CartManager.getInstance().getCartItems();
        
        // Since we are moving to RecyclerView for consistency
        adapter = new ProductRecyclerAdapter(getContext(), cartItems);
        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCart.setAdapter(adapter);

        updateTotal();

        Button btnCheckout = view.findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Thực hiện checkout đơn giản trực tiếp tại đây
            int userId = com.example.hitcapp.managers.SessionManager.userId;
            if (userId <= 0) {
                Toast.makeText(getContext(), "Vui lòng đăng nhập để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            String userName = com.example.hitcapp.managers.SessionManager.userName;
            double total = CartManager.getInstance().getTotalPrice();
            int qty = cartItems.size();

            if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            btnCheckout.setEnabled(false);

            APICaller.getInstance(getContext()).checkoutSimple(userId, userName, total, qty, new APICaller.OnApiResponse() {
                @Override
                public void onSuccess(String message) {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    btnCheckout.setEnabled(true);
                    Toast.makeText(getContext(), "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
                    CartManager.getInstance().clearCart();
                    adapter.notifyDataSetChanged();
                    updateTotal();
                }

                @Override
                public void onError(String error) {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    btnCheckout.setEnabled(true);
                    Toast.makeText(getContext(), "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

    private void updateTotal() {
        double total = CartManager.getInstance().getTotalPrice();
        tvTotal.setText(getString(R.string.price_format, total));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            updateTotal();
        }
    }
}
