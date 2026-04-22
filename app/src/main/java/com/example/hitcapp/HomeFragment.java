package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Product> productList;
    private List<Product> filteredList;
    private ProductAdapter adapter;
    private GridView gvProducts;
    private EditText etSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gvProducts = view.findViewById(R.id.gvProducts);
        etSearch = view.findViewById(R.id.etHomeSearch);


        initProductList();

        // Sao chép sang danh sách lọc
        filteredList = new ArrayList<>(productList);
        adapter = new ProductAdapter(getContext(), filteredList);
        gvProducts.setAdapter(adapter);


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        gvProducts.setOnItemClickListener((parent, v, position, id) -> {
            Product selectedProduct = filteredList.get(position);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("product_name", selectedProduct.getName());
            intent.putExtra("product_price", selectedProduct.getPrice());
            intent.putExtra("product_image", selectedProduct.getImageResource());
            startActivity(intent);
        });

        return view;
    }

    private void initProductList() {
        productList = new ArrayList<>();
        productList.add(new Product("Samsung Galaxy S26 Ultra", "$1,299", R.drawable.samsung_galaxy_s26_ultra));
        productList.add(new Product("iPhone 17 Pro Max", "$1,399", R.drawable.iphone17_promax));
        productList.add(new Product("Xiaomi Ultra 5G", "$999", R.drawable.xiaomi_ultra_5g));
        productList.add(new Product("MacBook Pro 14 M5 Pro", "$2,499", R.drawable.macbook_pro_14_m5_pro));
        productList.add(new Product("Apple Watch Series 11", "$499", R.drawable.apple_watch_11series));
    }

    private void filterProducts(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(productList);
        } else {
            for (Product p : productList) {
                if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(p);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}