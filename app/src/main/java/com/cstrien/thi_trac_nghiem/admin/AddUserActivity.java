package com.cstrien.thi_trac_nghiem.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.LoginActivity;
import com.cstrien.thi_trac_nghiem.model.Category;
import com.cstrien.thi_trac_nghiem.model.User;

public class AddUserActivity extends AppCompatActivity {
    private TextView txtUserName;

    private TextView btnHome;
    private ImageButton btnCreateUser;
    private EditText edtTaiKhoan;
    private EditText edtMatKhau;
    private ImageButton btnBackUser;
    private RadioGroup rdoGroup;
    private RadioButton rdoAdmin;
    private RadioButton rdoUser;
    private String user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        //
        addControls();
        Intent intent = getIntent();

        user_name = intent.getStringExtra("user");
        txtUserName.setText("Xin chào " + user_name);
        //


        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    User user = new User();
                    user.setName(edtTaiKhoan.getText().toString().trim());
                    user.setPassword(edtMatKhau.getText().toString().trim());
                    user.setRole(getRole());
                    if (addUser(user)) {
                        Toast.makeText(AddUserActivity.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(AddUserActivity.this, "Tạo tài khoản không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, MenuUserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnBackUser = findViewById(R.id.btnBackUser);
        btnCreateUser = findViewById(R.id.btnCreateUser);
        btnHome = findViewById(R.id.btnHome);
        txtUserName = findViewById(R.id.txtUserName);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoUser = findViewById(R.id.rdoUser);
        rdoGroup = findViewById(R.id.rdoGroup);
        btnHome = findViewById(R.id.btnHome);

        rdoUser.setChecked(true);
    }

    private boolean addUser(User user) {
        Database db = new Database(this);
        return db.signUp(user);
    }

    private boolean validateInput() {
        String taiKhoan = edtTaiKhoan.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();

        if (taiKhoan.isEmpty()) {
            Toast.makeText(AddUserActivity.this, "Chưa nhập tài khoản!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (matKhau.isEmpty()) {
            Toast.makeText(AddUserActivity.this, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (matKhau.length() < 3) {
            Toast.makeText(AddUserActivity.this, "Mật khẩu phải có ít nhất 3 kí tự!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkExistUser(taiKhoan)) {
            Toast.makeText(AddUserActivity.this, "Tên tài khoản đã được sử dụng!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private boolean checkExistUser(String name) {
        Database db = new Database(this);
        if (db.getUserByName(name).getName() != null)
            return false;
        return true;
    }

    private int getRole() {
        if (rdoAdmin.isChecked())
            return 1;
        return 0;
    }
}
