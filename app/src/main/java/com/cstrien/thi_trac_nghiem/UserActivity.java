package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
TextView txtTenU;
ImageView imgND;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        addControls();
        Bundle bundle=getIntent().getExtras();
        String name=bundle.getString("o");
        txtTenU.setText(name);
        imgND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        txtTenU=findViewById(R.id.txtTenU);
        imgND=findViewById(R.id.imgND);
    }
}