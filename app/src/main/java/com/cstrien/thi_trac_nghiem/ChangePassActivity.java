package com.cstrien.thi_trac_nghiem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.admin.AdminActivity;
import com.cstrien.thi_trac_nghiem.user.UserActivity;
import com.cstrien.thi_trac_nghiem.model.User;

public class ChangePassActivity extends AppCompatActivity {

    private TextView btnBack;
    private TextView btnDoiMatKhau;

    private EditText edtTaiKhoan;
    private EditText edtMatKhau;
    private EditText edtNhapLaiMatKhauMoi;
    private EditText edtMatKhauMoi;

    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        //
        anhXa();
        //
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        edtTaiKhoan.setText(user_name);
        //
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    String pass = edtMatKhauMoi.getText().toString().trim();

                    if (changePass(user_name, pass)) {
                        Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUserByName(user_name).getRole() == 1){
                    Intent intent = new Intent(ChangePassActivity.this, AdminActivity.class);
                    intent.putExtra("user", user_name);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ChangePassActivity.this, UserActivity.class);
                    intent.putExtra("user", user_name);
                    startActivity(intent);
                }
            }
        });

    }

    private void anhXa() {
        btnBack = findViewById(R.id.btnBack);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtMatKhauMoi = findViewById(R.id.edtMatKhauMoi);
        edtNhapLaiMatKhauMoi = findViewById(R.id.edtNhapLaiMatKhauMoi);
    }

    private User getUserByName(String user_name){
        Database db = new Database(this);
        return db.getUserByName(user_name);
    }

    private boolean changePass(String user_name, String pass){
        Database db = new Database(this);
        return db.changePass(user_name, pass);
    }
    private boolean validateInput() {
        String taiKhoan = edtTaiKhoan.getText().toString().trim();
        String matKhauCu = edtMatKhau.getText().toString().trim();
        String matKhauMoi = edtMatKhauMoi.getText().toString().trim();
        String nhapLaiMatKhauMoi = edtNhapLaiMatKhauMoi.getText().toString().trim();

        // Kiểm tra xem các trường đã được nhập hay chưa
        if (taiKhoan.isEmpty()) {
            setEditTextError(edtTaiKhoan, "Chưa nhập tài khoản!");
            return false;
        } else if (matKhauCu.isEmpty()) {
            setEditTextError(edtMatKhau, "Chưa nhập mật khẩu cũ!");
            return false;
        } else if (matKhauMoi.isEmpty()) {
            setEditTextError(edtMatKhauMoi, "Chưa nhập mật khẩu mới!");
            return false;
        } else if (nhapLaiMatKhauMoi.isEmpty()) {
            setEditTextError(edtNhapLaiMatKhauMoi, "Chưa nhập lại mật khẩu mới!");
            return false;
        } else if (matKhauMoi.length() < 3) { // Kiểm tra mật khẩu mới có ít nhất 3 kí tự
            setEditTextError(edtMatKhauMoi, "Mật khẩu mới phải có ít nhất 3 kí tự!");
            return false;
        } else if (!nhapLaiMatKhauMoi.equalsIgnoreCase(matKhauMoi)) { // Kiểm tra mật khẩu mới và nhập lại có trùng khớp hay không
            setEditTextError(edtNhapLaiMatKhauMoi, "Mật khẩu nhập lại không trùng khớp!");
            return false;
        }

        // Kiểm tra xem tài khoản và mật khẩu cũ có chính xác hay không
        Database db = new Database(this);
        User user = db.getUserByNameAndPass(taiKhoan, matKhauCu);
        if (user.getName() == null) {
            Toast.makeText(ChangePassActivity.this, "Tài khoản hoặc mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setEditTextError(EditText editText, String error) {
        editText.requestFocus();
        editText.setError(error);

    }



}