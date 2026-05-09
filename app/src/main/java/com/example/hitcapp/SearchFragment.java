package com.example.hitcapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hitcapp.adapters.ProductRecyclerAdapter;
import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText etSearch;
    private RecyclerView rvSearchResults;
    private ProgressBar progressBar;
    private List<Product> searchResults = new ArrayList<>();
    private ProductRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        rvSearchResults = view.findViewById(R.id.rvSearchResults);
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new ProductRecyclerAdapter(getContext(), searchResults);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchResults.setAdapter(adapter);

        // Hiển thị tất cả sản phẩm lúc đầu
        search("");

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void search(String query) {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        
        APICaller.getInstance(getContext()).searchProducts(query, new APICaller.OnProductLoaded() {
            @Override
            public void onLoaded(List<Product> productList) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                searchResults.clear();
                if (productList != null) {
                    searchResults.addAll(productList);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
