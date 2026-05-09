package com.example.hitcapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hitcapp.adapters.CategoryAdapter;
import com.example.hitcapp.adapters.ProductRecyclerAdapter;
import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.models.Category;
import com.example.hitcapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvCategories, rvProducts;
    private ProgressBar progressBar;
    private View cardSearch;
    
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> displayProducts = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    
    private ProductRecyclerAdapter productAdapter;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvCategories = view.findViewById(R.id.rvCategories);
        rvProducts = view.findViewById(R.id.rvProducts);
        progressBar = view.findViewById(R.id.progressBar);
        cardSearch = view.findViewById(R.id.cardSearch);

        if (cardSearch != null) {
            cardSearch.setOnClickListener(v -> {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity home = (HomeActivity) getActivity();
                    home.loadFragment(new SearchFragment());
                    home.updateNavUI(1);
                }
            });
        }

        categoryAdapter = new CategoryAdapter(getContext(), categoryList, category -> {
            filterProducts(category.getName());
        });
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(categoryAdapter);

        productAdapter = new ProductRecyclerAdapter(getContext(), displayProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(productAdapter);
        rvProducts.setNestedScrollingEnabled(false);
        
        fetchData();

        return view;
    }

    private void fetchData() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        APICaller.getInstance(getContext()).getCategory(new APICaller.OnCategoryLoaded() {
            @Override
            public void onLoaded(List<Category> categories) {
                categoryList.clear();
                Category all = new Category();
                all.setName("All");
                categoryList.add(all);
                if (categories != null) {
                    categoryList.addAll(categories);
                }
                categoryAdapter.notifyDataSetChanged();
                fetchProducts();
            }
        });
    }

    private void fetchProducts() {
        APICaller.getInstance(getContext()).getProducts(new APICaller.OnProductLoaded() {
            @Override
            public void onLoaded(List<Product> products) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                allProducts.clear();
                if (products != null) {
                    allProducts.addAll(products);
                }
                displayProducts.clear();
                displayProducts.addAll(allProducts);
                productAdapter.notifyDataSetChanged();
            }
        });
    }

    private void filterProducts(String categoryName) {
        displayProducts.clear();
        if (categoryName == null || categoryName.equalsIgnoreCase("All")) {
            displayProducts.addAll(allProducts);
        } else {
            for (Product p : allProducts) {
                if (p.getCategoryName() != null && p.getCategoryName().equalsIgnoreCase(categoryName)) {
                    displayProducts.add(p);
                }
            }
        }
        productAdapter.notifyDataSetChanged();
    }
}
