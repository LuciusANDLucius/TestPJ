package com.example.hitcapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hitcapp.CartManager;
import com.example.hitcapp.DetailActivity;
import com.example.hitcapp.R;
import com.example.hitcapp.models.Product;

import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;

    public ProductRecyclerAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (products == null || position >= products.size()) return;
        Product product = products.get(position);
        
        holder.tvName.setText(product.getName());
        holder.tvDescription.setText(product.getDescription());
        holder.tvPrice.setText(context.getString(R.string.price_format, product.getPrice()));
        holder.tvQty.setText("Stock: " + product.getQty());
        
        if (holder.tvCategory != null) {
            holder.tvCategory.setText(product.getCategoryName() != null ? product.getCategoryName() : "General");
        }

        // Image Handling: Reset image first to avoid wrong images due to recycling
        holder.imgProduct.setImageResource(R.drawable.icon_hitc);
        
        String base64Image = product.getImageBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                if (decodedByte != null) {
                    holder.imgProduct.setImageBitmap(decodedByte);
                }
            } catch (Exception e) {
                // Stay with default icon
            }
        }

        // Add to Cart Action
        if (holder.btnAddToCart != null) {
            holder.btnAddToCart.setOnClickListener(v -> {
                CartManager.getInstance().addProduct(product);
                Toast.makeText(context, "Added to cart: " + product.getName(), Toast.LENGTH_SHORT).show();
            });
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice, tvCategory, tvDescription, tvQty;
        ImageButton btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvQty = itemView.findViewById(R.id.tvQty);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
