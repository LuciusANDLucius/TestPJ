package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.List;

public class CartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        ListView lvCart = view.findViewById(R.id.lvCart);
        TextView tvTotal = view.findViewById(R.id.tvTotal);
        
        List<Product> cartItems = CartManager.getInstance().getCartItems();
        ProductAdapter adapter = new ProductAdapter(getContext(), cartItems);
        lvCart.setAdapter(adapter);

        double total = CartManager.getInstance().getTotalPrice();
        tvTotal.setText("$" + String.format("%.2f", total));

        Button btnCheckout = view.findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(getActivity(), PaymentActivity.class));
            }
        });

        return view;
    }
}