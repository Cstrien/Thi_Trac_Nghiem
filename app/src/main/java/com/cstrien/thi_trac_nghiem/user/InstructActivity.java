package com.cstrien.thi_trac_nghiem.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cstrien.thi_trac_nghiem.R;

public class InstructActivity extends AppCompatActivity {
    private TextView btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruct);
        addControls();
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InstructActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnHome=findViewById(R.id.btnHome)  ;
    }
}