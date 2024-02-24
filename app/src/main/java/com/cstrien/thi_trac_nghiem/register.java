package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class register extends AppCompatActivity {
    ImageButton imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControl();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addControl() {
        imgBack=findViewById(R.id.imgBack);
    }
}