package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnDangNhap,btnDangKi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
//sự kiện login
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });


    }

    private void addControl() {
        btnDangNhap=findViewById(R.id.btnDangNhap);
        btnDangKi=findViewById(R.id.btnDangKi);
    }
}