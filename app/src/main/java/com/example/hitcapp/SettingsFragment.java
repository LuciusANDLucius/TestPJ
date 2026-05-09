package com.example.hitcapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.hitcapp.managers.SessionManager;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPreferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        // Edit Profile removed

        // Change Password
        view.findViewById(R.id.btnChangePass).setOnClickListener(v -> {
            if (SessionManager.userId == 0) {
                Toast.makeText(getContext(), "Please login first", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
        });

        // Dark Mode Switch
        Switch switchDarkMode = view.findViewById(R.id.switchDarkMode);
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);
        switchDarkMode.setChecked(isDarkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("DarkMode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Logout
        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SessionManager.clear();
            CartManager.getInstance().clearCart();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}
