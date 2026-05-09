package com.example.hitcapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hitcapp.models.Product;

import java.util.List;

/**
 * ProductAdapter - Used by ListView (e.g., in SearchFragment)
 */
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
        TextView category = convertView.findViewById(R.id.tvCategory);
        TextView description = convertView.findViewById(R.id.tvDescription);
        TextView qty = convertView.findViewById(R.id.tvQty);
        ImageButton btnAddToCart = convertView.findViewById(R.id.btnAddToCart);

        name.setText(product.getName());
        price.setText(context.getString(R.string.price_format, product.getPrice()));
        if (description != null) description.setText(product.getDescription());
        if (qty != null) qty.setText("Stock: " + product.getQty());
        
        if (category != null) {
            category.setText(product.getCategoryName() != null ? product.getCategoryName() : "General");
        }

        // Image Handling - Fix: Reset image to default first to avoid wrong images on recycling
        img.setImageResource(R.drawable.icon_hitc);
        String base64String = product.getImageBase64();
        if (base64String != null && !base64String.isEmpty()) {
            try {
                byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                if (decodedByte != null) {
                    img.setImageBitmap(decodedByte);
                }
            } catch (Exception e) {
                // Keep default
            }
        }

        // Add to Cart Action
        if (btnAddToCart != null) {
            btnAddToCart.setOnClickListener(v -> {
                CartManager.getInstance().addProduct(product);
                Toast.makeText(context, "Added to cart: " + product.getName(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }
}
