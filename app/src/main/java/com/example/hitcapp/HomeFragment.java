package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView lvProducts = view.findViewById(R.id.lvProducts);
        List<Product> productList = new ArrayList<>();
        

        productList.add(new Product("Samsung Galaxy S26 Ultra", "$1,299", R.drawable.samsung_galaxy_s26_ultra));
        productList.add(new Product("iPhone 17 Pro Max", "$1,399", R.drawable.iphone17_promax));
        productList.add(new Product("Xiaomi Ultra 5G", "$999", R.drawable.xiaomi_ultra_5g));
        productList.add(new Product("MacBook Pro 14 M5 Pro", "$2,499", R.drawable.macbook_pro_14_m5_pro));
        productList.add(new Product("Apple Watch Series 11", "$499", R.drawable.apple_watch_11series));

        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
        lvProducts.setAdapter(adapter);

        lvProducts.setOnItemClickListener((parent, v, position, id) -> {
            Product selectedProduct = productList.get(position);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("product_name", selectedProduct.getName());
            intent.putExtra("product_price", selectedProduct.getPrice());
            intent.putExtra("product_image", selectedProduct.getImageResource());
            startActivity(intent);
        });

        return view;
    }
}