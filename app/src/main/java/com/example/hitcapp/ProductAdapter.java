package com.example.hitcapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        super(context, R.layout.item_product, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        Product product = products.get(position);

        ImageView img = convertView.findViewById(R.id.imgProduct);
        TextView name = convertView.findViewById(R.id.tvProductName);
        TextView price = convertView.findViewById(R.id.tvProductPrice);

        img.setImageResource(product.getImageResource());
        name.setText(product.getName());
        price.setText(product.getPrice());

        return convertView;
    }
}