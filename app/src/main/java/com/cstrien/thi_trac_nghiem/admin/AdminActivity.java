package com.cstrien.thi_trac_nghiem.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cstrien.thi_trac_nghiem.ChangePassActivity;
import com.cstrien.thi_trac_nghiem.LoginActivity;
import com.cstrien.thi_trac_nghiem.R;

public class AdminActivity extends AppCompatActivity {
    private TextView txtUserName;
    private TextView btnTaiKhoan;
    private TextView btnDangXuat;
    private TextView btnDoiMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Khởi tạo các thành phần giao diện
        addControls();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user");
        txtUserName.setText("Xin chào " + user_name);

        // Xử lý sự kiện khi nhấn nút "Đăng xuất"
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Đổi mật khẩu"
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ChangePassActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Tài khoản"
        btnTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MenuUserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
    }

    // Khởi tạo các thành phần giao diện
    private void addControls() {
        txtUserName = findViewById(R.id.txtUserName);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnTaiKhoan = findViewById(R.id.btnTaiKhoan);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
    }
}