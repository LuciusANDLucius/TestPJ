package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private String[] items = new String[]{
            "Cấu hình hệ thống",
            "Quản lý tài khoản",
            "Chế độ giao diện",
            "Thông báo",
            "Ngôn ngữ",
            "Trợ giúp",
            "Đăng xuất"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ListView listView = view.findViewById(R.id.lvSettings);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            String selected = items[position];
            if (selected.equals("Đăng xuất")) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), selected, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}