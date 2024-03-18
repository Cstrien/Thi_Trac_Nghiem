package com.cstrien.thi_trac_nghiem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.user.UserActivity;
import com.cstrien.thi_trac_nghiem.model.User;
import com.cstrien.thi_trac_nghiem.admin.AdminActivity;

public class LoginActivity extends AppCompatActivity {

    private TextView btnDangNhap;
    private TextView btnDangKy;
    private EditText edtTaiKhoan;
    private EditText edtMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //
        anhXa();
        //
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    String name = edtTaiKhoan.getText().toString();
                    String pass = edtMatKhau.getText().toString();
                    User user = checkLogin(name, pass);
                    if (user.getName() != null) {
                        if (user.getRole() == 1) {
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            intent.putExtra("user", name);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            intent.putExtra("user", name);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private User checkLogin(String name, String pass) {
        Database db = new Database(this);
        User user = db.getUserByNameAndPass(name, pass);
        if (user != null) return user;
        return null;
    }

    private void anhXa() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
    }

    private boolean validateInput() {
        if (edtTaiKhoan.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Chưa nhập tài khoản!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtMatKhau.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}