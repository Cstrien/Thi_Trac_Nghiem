package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class login extends AppCompatActivity {
    ImageButton imgBack;
    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //sự kiện đăng login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                // Kiểm tra thông tin đăng nhập từ cơ sở dữ liệu
                if (checkLogin(username, password)) {
                    // Nếu đăng nhập thành công, thực hiện hành động tương ứng (ví dụ: chuyển hướng đến màn hình chính)
                    //  saveLoginInfo(username,password);
                    Intent intent = new Intent(login.this, Admin_taikhoan.class);
                    intent.putExtra("o", username);
                    startActivity(intent);
                    finish(); // Đóng activity đăng nhập
                } else {
                    // Hiển thị thông báo lỗi đăng nhập không thành công
                    Toast.makeText(login.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean checkLogin(String username, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // Mở cơ sở dữ liệu
            db = openOrCreateDatabase("THI_TRAC_NGHIEM_DATA", MODE_PRIVATE, null);

            // Truy vấn cơ sở dữ liệu để kiểm tra thông tin đăng nhập (sử dụng tham số hóa)
            String query = "SELECT * FROM SinhVien WHERE Username = ? AND Password = ?";
            cursor = db.rawQuery(query, new String[]{username, password});


            // Trong phương thức checkLogin:
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("Role");
                if (columnIndex != -1) {
                    String role = cursor.getString(columnIndex);
                    if (role.equals("student")) {
                        // Chuyển hướng đến trang sinh viên
                        Intent intent = new Intent(login.this, UserActivity.class);
                        startActivity(intent);
                        finish(); // Kết thúc activity đăng nhập để không quay lại khi nhấn nút back
                    } else if (role.equals("lecturer") || role.equals("admin")) {
                        // Chuyển hướng đến trang admin
                        Intent intent = new Intent(login.this, Admin_taikhoan.class);
                        startActivity(intent);
                        finish(); // Kết thúc activity đăng nhập để không quay lại khi nhấn nút back
                    } else {
                        // Xử lý trường hợp khác
                        Toast.makeText(login.this, "Vai trò không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }

        catch (Exception e) {
            // Xử lý các trường hợp lỗi (ví dụ: in log, hiển thị thông báo lỗi)
            e.printStackTrace();
                        }
        finally {
            // Đảm bảo đóng cơ sở dữ liệu và cursor sau khi sử dụng
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return true;
    }

    private void addControl() {
        btnLogin=findViewById(R.id.btnLogin);
        imgBack=findViewById(R.id.imgBack);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
    }
  
}