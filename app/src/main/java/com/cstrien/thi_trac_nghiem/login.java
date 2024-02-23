package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class login extends AppCompatActivity {
    ImageButton imgBack;
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
            }
        });

    }

    private void addControl() {
        btnLogin=findViewById(R.id.btnLogin);
        imgBack=findViewById(R.id.imgBack);
    }
}