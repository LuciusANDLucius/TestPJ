package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    private static String[] items = new String[]{
            "Cấu hình hệ thống",
            "Quản lý tài khoản",
            "Chế độ giao diện (Sáng/Tối)",
            "Thông báo & Âm thanh",
            "Quyền riêng tư & Bảo mật",
            "Ngôn ngữ ứng dụng",
            "Dung lượng & Dữ liệu",
            "Trợ giúp & Hỗ trợ",
            "Điều khoản dịch vụ",
            "Thông tin phiên bản",
            "Đăng xuất"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button backtohome = findViewById(R.id.backtohome);
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ListView listView = findViewById(R.id.myList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, items
        );
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items[position];
                
                if (selectedItem.equals("Đăng xuất")) {

                    Intent intent = new Intent(Setting.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(Setting.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Setting.this, "Bạn đã chọn: " + selectedItem, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}