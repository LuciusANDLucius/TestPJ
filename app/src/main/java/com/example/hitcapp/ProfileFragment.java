package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Lấy dữ liệu từ Bundle gửi từ HomeActivity
        Bundle bundle = getArguments();
        String email = bundle != null ? bundle.getString("USER_EMAIL") : "Guest User";
        boolean isGuest = bundle != null && bundle.getBoolean("IS_GUEST", false);

        TextView tvEmail = view.findViewById(R.id.tvUserEmail);
        TextView tvName = view.findViewById(R.id.tvUserName);

        if (isGuest) {
            tvName.setText("Guest User");
            tvEmail.setText("Not connected");
        } else {
            tvName.setText("Member");
            tvEmail.setText(email);
        }

        view.findViewById(R.id.btnChangePass).setOnClickListener(v -> {
            if (isGuest) {
                Toast.makeText(getContext(), "Please login first", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        return view;
    }
}