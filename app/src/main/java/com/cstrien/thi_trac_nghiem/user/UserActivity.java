package com.cstrien.thi_trac_nghiem.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.ChangePassActivity;
import com.cstrien.thi_trac_nghiem.LoginActivity;

public class UserActivity extends AppCompatActivity {
    private TextView btnReady;
    private TextView btnGuide;

    private TextView btnDoiMatKhau;
    private TextView txtUserName;
    private TextView btnDangXuat;


    private String user_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //
        anhXa();
        //
        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                intent.putExtra("Xin chào " + "user", user_name);
                startActivity(intent);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ChangePassActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserActivity.this, InstructActivity.class);
                startActivity(intent);
            }
        });

    }

    private void anhXa() {
        btnReady = findViewById(R.id.btnReady);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnGuide = findViewById(R.id.btnGuide);
        btnDangXuat = findViewById(R.id.btnDangXuat);

        txtUserName = findViewById(R.id.txtUserName);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        txtUserName.setText(user_name);


    }
}
