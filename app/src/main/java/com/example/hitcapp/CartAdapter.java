package com.example.hitcapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class CartAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> cartItems;
    private OnCartChangedListener listener;

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    public CartAdapter(Context context, List<Product> cartItems, OnCartChangedListener listener) {
        super(context, R.layout.item_cart, cartItems);
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        Product item = cartItems.get(position);

        ImageView img = convertView.findViewById(R.id.imgCart);
        TextView name = convertView.findViewById(R.id.tvCartName);
        TextView price = convertView.findViewById(R.id.tvCartPrice);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        TextView btnPlus = convertView.findViewById(R.id.btnPlus);
        TextView btnMinus = convertView.findViewById(R.id.btnMinus);
        ImageView btnRemove = convertView.findViewById(R.id.btnRemove);

        img.setImageResource(item.getImageResource());
        name.setText(item.getName());
        price.setText(item.getPrice());
        tvQuantity.setText(String.valueOf(item.getQuantity()));

        btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyDataSetChanged();
            if (listener != null) listener.onCartChanged();
        });

        btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyDataSetChanged();
                if (listener != null) listener.onCartChanged();
            }
        });

        btnRemove.setOnClickListener(v -> {
            CartManager.getInstance().removeProduct(position);
            notifyDataSetChanged();
            if (listener != null) listener.onCartChanged();
        });

        return convertView;
    }
}