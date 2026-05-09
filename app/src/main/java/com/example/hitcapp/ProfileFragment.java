package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.hitcapp.api.APICaller;
import com.example.hitcapp.managers.SessionManager;
import com.example.hitcapp.models.User;

public class ProfileFragment extends Fragment {

    private TextView tvId, tvFirstName, tvLastName, tvUsername, tvEmail;
    private ProgressBar progressBar;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userId = SessionManager.userId;

        tvId = view.findViewById(R.id.tvId);
        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvLastName = view.findViewById(R.id.tvLastName);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        progressBar = view.findViewById(R.id.progressBar);

        if (userId > 0) {
            fetchUserProfile();
        } else {
            tvUsername.setText("Guest");
        }

        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SessionManager.clear();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    private void fetchUserProfile() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        APICaller.getInstance(getContext()).getUserById(userId, new APICaller.OnUserLoaded() {
            @Override
            public void onSuccess(User user) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                if (user != null) {
                    tvId.setText(String.valueOf(user.getId()));
                    tvFirstName.setText(user.getFirstName());
                    tvLastName.setText(user.getLastName());
                    tvUsername.setText(user.getUserName());
                    tvEmail.setText(user.getEmailId());
                }
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error loading info user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
