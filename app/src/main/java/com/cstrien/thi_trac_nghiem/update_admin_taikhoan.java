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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin_taikhoan);
        addControls();
        addEnvents();
    }

    private void addEnvents() {



    }


    private void addControls() {
        edt_Mssv = findViewById(R.id.edtMssvUpdate);
        edt_TenSV = findViewById(R.id.edtTenSVUpdate);
        edt_Username = findViewById(R.id.edtUsernameUpdate);
        edt_Password = findViewById(R.id.edtPasswordUpdate);


    }
    private void createDatabase() {
        database = openOrCreateDatabase("THI_TRAC_NGHIEM_DATA", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS SinhVien (SinhvienID TEXT PRIMARY KEY, TenSV TEXT, Username TEXT, Password TEXT)");
    }
}