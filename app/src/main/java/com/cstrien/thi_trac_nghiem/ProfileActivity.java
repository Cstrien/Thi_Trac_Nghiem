package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
TextView edtuserU,edtmaU,edtpassU,edtComfirmPassU,edtnameU;
SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        addControls();
        createDatabase();
        fetchDataFromDatabase();

    }
    private void fetchDataFromDatabase() {
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM SinhVien", null);

            if (cursor.moveToFirst()) {
                // Lấy dữ liệu từ   Cursor
                int nameColumnIndex = cursor.getColumnIndex("TenSV");
                int usernameColumnIndex = cursor.getColumnIndex("Username");
                int IDColumnIndex = cursor.getColumnIndex("SinhvienID");
                int passColumnIndex = cursor.getColumnIndex("Password");

                if (nameColumnIndex != -1 && usernameColumnIndex != -1&&IDColumnIndex != -1 && passColumnIndex != -1) {
                    // Kiểm tra xem các cột có tồn tại trong Cursor không
                    String name = cursor.getString(nameColumnIndex);
                    String username = cursor.getString(usernameColumnIndex);
                    String IDname = cursor.getString(IDColumnIndex);
                    String pass = cursor.getString(passColumnIndex);

                    // Gán dữ liệu vào các TextView hoặc thành phần khác tương tự
                    edtnameU.setText(name);
                    edtuserU.setText(username);
                    edtmaU.setText(IDname);
                    edtpassU.setText(pass);
                    // Các TextView khác tương tự
                } else {
                    // Nếu các cột không tồn tại trong Cursor
                    Toast.makeText(this, "Lỗi: Cột không tồn tại trong Cursor", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Không có dữ liệu
                Toast.makeText(this, "Không có dữ liệu trong bảng SinhVien.", Toast.LENGTH_SHORT).show();
            }

            // Đóng Cursor sau khi sử dụng
            cursor.close();

        } catch (Exception e) {
            // Xử lý lỗi khi truy vấn cơ sở dữ liệu
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi truy vấn cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
    private void createDatabase() {
        // Tạo hoặc mở cơ sở dữ liệu
        database = openOrCreateDatabase("THI_TRAC_NGHIEM_DATA", MODE_PRIVATE, null);
        // Tạo bảng nếu chưa tồn tại
        database.execSQL("CREATE TABLE IF NOT EXISTS SinhVien (SinhvienID TEXT PRIMARY KEY, Username TEXT, Password TEXT)");
    }
    private void addControls() {
        edtuserU=findViewById(R.id.eduuserU);
        edtmaU=findViewById(R.id.edtmaU);
        edtpassU=findViewById(R.id.edtpassU);
        edtComfirmPassU=findViewById(R.id.edtComfirmPassU);
        edtnameU=findViewById(R.id.edtnameU);
    }
}