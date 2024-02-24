package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    EditText edtUsername,edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this, MainActivity.class );
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
                    Intent intent = new Intent(login.this, Admin.class);
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

            // Kiểm tra xem cursor có dữ liệu không (nếu có, tức là đăng nhập thành công)
            return cursor.moveToFirst();
        } catch (Exception e) {
            // Xử lý các trường hợp lỗi (ví dụ: in log, hiển thị thông báo lỗi)
            e.printStackTrace();
            return false;
        } finally {
            // Đảm bảo đóng cơ sở dữ liệu và cursor sau khi sử dụng
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }



    private void addControl() {
        btnLogin=findViewById(R.id.btnLogin);
        imgBack=findViewById(R.id.imgBack);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
    }
}