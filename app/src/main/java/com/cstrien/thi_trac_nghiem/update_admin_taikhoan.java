package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class update_admin_taikhoan extends AppCompatActivity {
    private static final String DATABASE_NAME = "THI_TRAC_NGHIEM_DATA";
    EditText edt_Username, edt_Password, edt_Mssv, edt_TenSV;
    SQLiteDatabase database;
    Button btn_update, btn_Xoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin_taikhoan);
        addControls();
        addEnvents();
    }

    private void addEnvents() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ các EditText
                String mssv = edt_Mssv.getText().toString();
                String tenSV = edt_TenSV.getText().toString();
                String username = edt_Username.getText().toString();
                String password = edt_Password.getText().toString();

                // Thực hiện cập nhật thông tin tài khoản trong cơ sở dữ liệu
                ContentValues values = new ContentValues();
                values.put("SinhvienID",mssv);
                values.put("TenSV", tenSV);
                values.put("Username", username);
                values.put("Password", password);

                int rowsAffected = database.update("SinhVien", values, "SinhvienID = ?", new String[]{mssv});
                if (rowsAffected > 0) {
                    Toast.makeText(update_admin_taikhoan.this, "Đã cập nhật thông tin tài khoản", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(update_admin_taikhoan.this, "Cập nhật thông tin tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy MSSV từ EditText
                String mssv = edt_Mssv.getText().toString();
                String TenSV=edt_TenSV.getText().toString();

                // Thực hiện xóa tài khoản từ cơ sở dữ liệu
                int rowsAffected = database.delete("SinhVien", "SinhvienID = ?", new String[]{TenSV});
                if (rowsAffected > 0) {
                    Toast.makeText(update_admin_taikhoan.this, "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(update_admin_taikhoan.this, "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void addControls() {
        edt_Mssv = findViewById(R.id.edtMssvUpdate);
        edt_TenSV = findViewById(R.id.edtTenSVUpdate);
        edt_Username = findViewById(R.id.edtUsernameUpdate);
        edt_Password = findViewById(R.id.edtPasswordUpdate);
        btn_update = findViewById(R.id.btnUpdate);
        btn_Xoa = findViewById(R.id.btnXoa);
    }
    private void createDatabase() {
        database = openOrCreateDatabase("THI_TRAC_NGHIEM_DATA", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS SinhVien (SinhvienID TEXT PRIMARY KEY, TenSV TEXT, Username TEXT, Password TEXT)");
    }
}