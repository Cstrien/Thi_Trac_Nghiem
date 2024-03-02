package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
TextView edtuserU,edtmaU,edtpassU,edtnameU;
SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        addControls();
        fetchDataFromDatabase();

    }
    private void fetchDataFromDatabase() {
        try {
            // Lấy thông tin từ SharedPreferences
            SharedPreferences preferences = getSharedPreferences("THI_TRAC_NGHIEM_DATA", MODE_PRIVATE);
            String ID = preferences.getString("SinhvienID", "");
            String name = preferences.getString("TenSV", "");
            String pass = preferences.getString("Password", "");
            String username = preferences.getString("Username", "");

            // Hiển thị thông tin lên các TextView
            edtnameU.setText(name);
            edtuserU.setText(username);
            edtmaU.setText(ID);
            edtpassU.setText(pass);
            // Các TextView khác tương tự
        } catch (Exception e) {
            // Xử lý lỗi khi truy xuất SharedPreferences
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi truy xuất SharedPreferences", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        edtuserU=findViewById(R.id.eduuserU);
        edtmaU=findViewById(R.id.edtmaU);
        edtpassU=findViewById(R.id.edtpassU);

        edtnameU=findViewById(R.id.edtnameU);
    }
}