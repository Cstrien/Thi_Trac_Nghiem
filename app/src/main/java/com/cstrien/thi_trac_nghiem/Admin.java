package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Admin extends AppCompatActivity {
    ImageView imgTaikhoan;
    ImageView imgCauhoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControls();


        imgTaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Admin.this,Admin_taikhoan.class);
                startActivity(intent);
            }
        });
    }


    private void addControls() {

        imgTaikhoan=findViewById(R.id.imgTaikhoan);
        imgCauhoi=findViewById(R.id.imgCauhoi);
    }
}