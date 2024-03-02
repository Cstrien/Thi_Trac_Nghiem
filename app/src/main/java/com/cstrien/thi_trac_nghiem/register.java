package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class register extends AppCompatActivity {
    ImageButton imgBack;
    Button btnRegister,btnCancel;
    EditText edtName,edtMssv,edtUsername,edtPassword,edtComfirmPassword;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControl();
        createDatabase();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mssv=edtMssv.getText().toString();
                String name=edtName.getText().toString();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtComfirmPassword.getText().toString();

                // Kiểm tra xác nhận mật khẩu
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(register.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm tài khoản vào cơ sở dữ liệu
                if (registerAccount(mssv,name,username, password)) {
                    Toast.makeText(register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    // Chuyển hướng về màn hình đăng nhập sau khi đăng ký thành công
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(register.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean registerAccount(String mssv,String name,String username, String password) {
        try {
            // Tạo đối tượng ContentValues để chứa dữ liệu cần thêm vào cơ sở dữ liệu
            ContentValues values = new ContentValues();
            values.put("SinhvienID",mssv);
            values.put("TenSV",name);
            values.put("Username", username);
            values.put("Password", password);
            // Thêm dữ liệu vào bảng SinhVien
            database.insert("SinhVien", null, values);
            // sử dụng SharedPreferences để lưu dữ liệu
            SharedPreferences preferences = getSharedPreferences("THI_TRAC_NGHIEM_DATA", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("SinhvienID", mssv);
            editor.putString("Username", username);
            editor.putString("TenSV", name);
            editor.putString("Password", password);
            editor.apply();
            editor.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void createDatabase() {
        // Tạo hoặc mở cơ sở dữ liệu
        database = openOrCreateDatabase("THI_TRAC_NGHIEM_DATA", MODE_PRIVATE, null);
        // Tạo bảng nếu chưa tồn tại
        database.execSQL("CREATE TABLE IF NOT EXISTS SinhVien (SinhvienID TEXT PRIMARY KEY, Username TEXT, Password TEXT)");
    }

    private void addControl() {
        imgBack=findViewById(R.id.imgBack);
        btnRegister=findViewById(R.id.btnRegister);
        btnCancel=findViewById(R.id.btnCancel);
        edtName=findViewById(R.id.edtName);
        edtMssv=findViewById(R.id.edtMssv);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        edtComfirmPassword=findViewById(R.id.edtComfirmPassword);

    }
}