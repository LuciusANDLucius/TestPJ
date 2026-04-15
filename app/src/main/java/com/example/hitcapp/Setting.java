package com.example.hitcapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView backtohome = findViewById(R.id.backtohome);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        ListView listView = findViewById(R.id.myList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_1,items
        );




    listView.setAdapter(adapter);
    }
}