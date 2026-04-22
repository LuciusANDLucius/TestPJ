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

public class CartFragment extends Fragment implements CartAdapter.OnCartChangedListener {

    private TextView tvTotal;
    private CartAdapter adapter;
    private List<Product> cartItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        ListView lvCart = view.findViewById(R.id.lvCart);
        tvTotal = view.findViewById(R.id.tvTotal);
        
        cartItems = CartManager.getInstance().getCartItems();
        adapter = new CartAdapter(getContext(), cartItems, this);
        lvCart.setAdapter(adapter);

        updateTotal();

        Button btnCheckout = view.findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            boolean isGuest = false;
            if (getActivity() != null && getActivity().getIntent() != null) {
                isGuest = getActivity().getIntent().getBooleanExtra("IS_GUEST", false);
            }

            if (cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else if (isGuest) {
                Toast.makeText(getContext(), "Please login to proceed to payment!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                startActivity(new Intent(getActivity(), PaymentActivity.class));
            }
        });

        return view;
    }

    private void updateTotal() {
        double total = CartManager.getInstance().getTotalPrice();
        tvTotal.setText("$" + String.format("%.2f", total));
    }

    @Override
    public void onCartChanged() {
        updateTotal();
    }
}