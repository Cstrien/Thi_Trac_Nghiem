package com.cstrien.thi_trac_nghiem.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.LoginActivity;
import com.cstrien.thi_trac_nghiem.model.Category;
import com.cstrien.thi_trac_nghiem.model.Question;

import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {
    private TextView txtUserName;

    private TextView btnHome;
    private ImageButton btnCreateCategory;
    private EditText edtNameCategory;
    private ImageButton btnBackCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        //
        anhXa();
        //
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user");
        txtUserName.setText("Xin chào " + user_name);
        //

        btnCreateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Category category = new Category();
                    category.setName(edtNameCategory.getText().toString().trim());
                    if (addCategory(category)) {
                        Toast.makeText(AddCategoryActivity.this, "Thêm chủ đề thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(AddCategoryActivity.this, "Thêm chủ đề không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //
        btnBackCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCategoryActivity.this, MenuCategoryActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCategoryActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

    }

    private boolean addCategory(Category category) {
        Database db = new Database(this);
        return db.addCategory(category);
    }

    private void anhXa() {
        txtUserName = findViewById(R.id.txtUserName);
        btnBackCategory = findViewById(R.id.btnBackCategory);
        btnHome = findViewById(R.id.btnHome);
        btnCreateCategory = findViewById(R.id.btnCreateCategory);
        edtNameCategory = findViewById(R.id.edtNameCategory);
    }


    private boolean validateInput() {
        if (edtNameCategory.getText().toString().trim().isEmpty()) {
            Toast.makeText(AddCategoryActivity.this, "Chưa nhập tên chủ đề!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
