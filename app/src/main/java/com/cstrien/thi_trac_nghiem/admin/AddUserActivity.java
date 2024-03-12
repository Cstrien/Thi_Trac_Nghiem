package com.cstrien.thi_trac_nghiem.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.model.User;

import java.util.ArrayList;

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

        // Xử lý sự kiện khi nhấn nút "Tạo tài khoản"
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
                    } else {
                        Toast.makeText(AddUserActivity.this, "Tạo tài khoản không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút "Quay lại"
        btnBackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, MenuUserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Trang chính"
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
    }

    // Khởi tạo các thành phần giao diện
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

    // Thêm người dùng vào cơ sở dữ liệu
    private boolean addUser(User user) {
        Database db = new Database(this);
        return db.signUp(user);
    }

    // Kiểm tra tính hợp lệ của dữ liệu nhập vào
    private boolean validateInput() {
        if (edtTaiKhoan.getText().toString().trim().isEmpty()) {
            Toast.makeText(AddUserActivity.this, "Chưa nhập tài khoản!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtMatKhau.getText().toString().trim().isEmpty()) {
            Toast.makeText(AddUserActivity.this, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkExistUser(edtTaiKhoan.getText().toString().trim())) {
            Toast.makeText(AddUserActivity.this, "Tên tài khoản đã được sử dụng!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Kiểm tra sự tồn tại của người dùng trong cơ sở dữ liệu
    private boolean checkExistUser(String name) {
        Database db = new Database(this);
        if (db.getUserByName(name).getName() != null)
            return false;
        return true;
    }

    // Lấy vai trò của người dùng
    private int getRole() {
        if (rdoAdmin.isChecked())
            return 1;
        return 0;
    }
}