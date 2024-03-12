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
import com.cstrien.thi_trac_nghiem.model.User;

public class EditUserActivity extends AppCompatActivity {
    // Khai báo các biến và thành phần giao diện
    private TextView txtUserName;
    private TextView btnHome;
    private ImageButton btnEditUser;
    private ImageButton btnBackUser;
    private EditText edtTaiKhoan;
    private EditText edtMatKhau;
    private RadioGroup rdoGroup;
    private RadioButton rdoAdmin;
    private RadioButton rdoUser;
    private String user_name;
    private int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Khởi tạo các thành phần giao diện
        addControls();

        // Xử lý sự kiện khi nhấn nút "Quay lại"
        btnBackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserActivity.this, MenuUserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Chỉnh sửa tài khoản"
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    User user = new User();
                    user.setId(id_user);
                    user.setName(edtTaiKhoan.getText().toString().trim());
                    user.setPassword(edtMatKhau.getText().toString().trim());

                    // Xác định vai trò của người dùng
                    if(rdoAdmin.isChecked())
                        user.setRole(1);
                    else
                        user.setRole(0);

                    // Cập nhật thông tin tài khoản
                    if (updateUser(user)) {
                        Toast.makeText(EditUserActivity.this, "Cập nhật tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditUserActivity.this, "Cập nhật tài khoản không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút "Trang chính"
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
    }

    // Khởi tạo các thành phần giao diện
    private void addControls() {
        txtUserName = findViewById(R.id.txtUserName);
        btnEditUser = findViewById(R.id.btnEditUser);
        btnBackUser = findViewById(R.id.btnBackUser);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoUser = findViewById(R.id.rdoUser);
        rdoGroup = findViewById(R.id.rdoGroup);
        btnHome = findViewById(R.id.btnHome);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        id_user = intent.getIntExtra("id_user", 0);
        txtUserName.setText("Xin chào " + user_name);

        // Lấy thông tin người dùng theo ID và hiển thị lên giao diện
        User u = getUserById(id_user);
        edtTaiKhoan.setText(u.getName());
        edtMatKhau.setText(u.getPassword());
        if (u.getRole() == 1)
            rdoAdmin.setChecked(true);
        else
            rdoUser.setChecked(true);
    }

    // Cập nhật thông tin người dùng
    private boolean updateUser(User user) {
        Database db = new Database(this);
        return db.updateUser(user);
    }

    // Kiểm tra tính hợp lệ của dữ liệu nhập vào
    private boolean validateInput() {
        if (edtMatKhau.getText().toString().trim().isEmpty()) {
            Toast.makeText(EditUserActivity.this, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Lấy thông tin người dùng theo ID
    private User getUserById(int id) {
        Database db = new Database(this);
        return db.getUserById(id);
    }
}
