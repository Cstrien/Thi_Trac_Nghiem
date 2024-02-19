package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnDangNhap,btnDangKi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();

    }

    private void addControl() {
        btnDangNhap=findViewById(R.id.btnDangNhap);
        btnDangKi=findViewById(R.id.btnDangKi);
    }
}