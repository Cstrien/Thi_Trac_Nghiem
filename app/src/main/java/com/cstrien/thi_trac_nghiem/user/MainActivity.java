package com.cstrien.thi_trac_nghiem.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.LoginActivity;
import com.cstrien.thi_trac_nghiem.model.Category;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView btnHome;
    private TextView txtUserName;
    private Spinner spCategory;
    private TextView btnGuide;
    private TextView btnStart;

    private TextView btnBack;


    private ArrayList<Category> listCategories;

    private String user_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        addControls();
        //

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuestion();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });


    }

    private void addControls() {

        btnHome = findViewById(R.id.btnHome);
        txtUserName = findViewById(R.id.txtUserName);
        spCategory = findViewById(R.id.spCategory);
        btnStart = findViewById(R.id.btnStart);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        txtUserName.setText(user_name);

        loadCategories();


    }

    private void startQuestion() {
        Category category = (Category) spCategory.getSelectedItem();
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        String categoryName = category.getName();
        int categoryID = category.getId();
        intent.putExtra("user", user_name);
        intent.putExtra("categoryName", categoryName);
        intent.putExtra("categoryID", categoryID);
        startActivity(intent);
    }

    private void loadCategories() {
        Database db = new Database(this);
        ArrayList<Category> listCategories = db.getListCategories(null);
        // tạo adapter
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCategories);
        // bố cục
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // gắn chủ đề lên spinner hiển thị
        spCategory.setAdapter(categoryArrayAdapter);
    }



}
