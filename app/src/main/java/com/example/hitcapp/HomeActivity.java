package com.example.hitcapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout navHome, navSearch, navCart, navProfile, navSettings;
    private ImageView ivHome, ivSearch, ivCart, ivProfile, ivSettings;
    private TextView tvHome, tvSearch, tvCart, tvProfile, tvSettings;
    private String userEmail;
    private boolean isGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userEmail = getIntent().getStringExtra("USER_EMAIL");
        isGuest = getIntent().getBooleanExtra("IS_GUEST", false);

        initViews();
        loadFragment(new HomeFragment());

        navHome.setOnClickListener(v -> { loadFragment(new HomeFragment()); updateNavUI(0); });
        navSearch.setOnClickListener(v -> { loadFragment(new SearchFragment()); updateNavUI(1); });
        navCart.setOnClickListener(v -> { loadFragment(new CartFragment()); updateNavUI(2); });
        
        navProfile.setOnClickListener(v -> {
            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putString("USER_EMAIL", userEmail);
            bundle.putBoolean("IS_GUEST", isGuest);
            profileFragment.setArguments(bundle);
            loadFragment(profileFragment);
            updateNavUI(3);
        });

        navSettings.setOnClickListener(v -> { loadFragment(new SettingsFragment()); updateNavUI(4); });
    }

    private void initViews() {
        navHome = findViewById(R.id.navHome);
        navSearch = findViewById(R.id.navSearch);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);
        navSettings = findViewById(R.id.navSettings);
        ivHome = findViewById(R.id.ivHome);
        ivSearch = findViewById(R.id.ivSearch);
        ivCart = findViewById(R.id.ivCart);
        ivProfile = findViewById(R.id.ivProfile);
        ivSettings = findViewById(R.id.ivSettings);
        tvHome = findViewById(R.id.tvHome);
        tvSearch = findViewById(R.id.tvSearch);
        tvCart = findViewById(R.id.tvCart);
        tvProfile = findViewById(R.id.tvProfile);
        tvSettings = findViewById(R.id.tvSettings);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void updateNavUI(int selectedIndex) {
        int gray = Color.parseColor("#888888");
        int purple = Color.parseColor("#6200EE");
        ivHome.setColorFilter(gray); tvHome.setTextColor(gray);
        ivSearch.setColorFilter(gray); tvSearch.setTextColor(gray);
        ivCart.setColorFilter(gray); tvCart.setTextColor(gray);
        ivProfile.setColorFilter(gray); tvProfile.setTextColor(gray);
        ivSettings.setColorFilter(gray); tvSettings.setTextColor(gray);
        switch (selectedIndex) {
            case 0: ivHome.setColorFilter(purple); tvHome.setTextColor(purple); break;
            case 1: ivSearch.setColorFilter(purple); tvSearch.setTextColor(purple); break;
            case 2: ivCart.setColorFilter(purple); tvCart.setTextColor(purple); break;
            case 3: ivProfile.setColorFilter(purple); tvProfile.setTextColor(purple); break;
            case 4: ivSettings.setColorFilter(purple); tvSettings.setTextColor(purple); break;
        }
    }
}