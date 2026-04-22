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
        productList.add(new Product("iPhone 15 Pro", "$999", R.drawable.icon_hitc));
        productList.add(new Product("Samsung S24 Ultra", "$1199", R.drawable.icon_hitc));
        productList.add(new Product("MacBook Air M3", "$1299", R.drawable.icon_hitc));
        productList.add(new Product("Sony WH-1000XM5", "$349", R.drawable.icon_hitc));
        productList.add(new Product("iPad Pro 12.9", "$1099", R.drawable.icon_hitc));
        productList.add(new Product("Apple Watch Series 9", "$399", R.drawable.icon_hitc));
        productList.add(new Product("Nintendo Switch OLED", "$349", R.drawable.icon_hitc));
        productList.add(new Product("Dell XPS 15", "$1599", R.drawable.icon_hitc));
        productList.add(new Product("Logitech MX Master 3S", "$99", R.drawable.icon_hitc));
        productList.add(new Product("Keychron K2 V2", "$79", R.drawable.icon_hitc));

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