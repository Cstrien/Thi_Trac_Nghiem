package com.cstrien.thi_trac_nghiem.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.LoginActivity;
import com.cstrien.thi_trac_nghiem.model.User;


public class EditUserActivity extends AppCompatActivity {
    private TextView txtUserName;

    private TextView btnHome;

    private Spinner spCategory;
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
        //
        anhXa();

        //
        btnBackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserActivity.this, MenuUserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    User user = new User();
                    user.setId(id_user);
                    user.setName(edtTaiKhoan.getText().toString().trim());
                    user.setPassword(edtMatKhau.getText().toString().trim());

                    if(rdoAdmin.isChecked()) user.setRole(1);
                    else user.setRole(0);

                    if (updateUser(user)) {
                        Toast.makeText(EditUserActivity.this, "Cập nhật tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(EditUserActivity.this, "Cập nhật tài khoản không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

    }


    private void anhXa() {
        txtUserName = findViewById(R.id.txtUserName);
        spCategory = findViewById(R.id.spCategory);

        btnEditUser = findViewById(R.id.btnEditUser);
        btnBackUser = findViewById(R.id.btnBackUser);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoUser = findViewById(R.id.rdoUser);
        rdoGroup = findViewById(R.id.rdoGroup);
        btnHome = findViewById(R.id.btnHome);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        id_user = intent.getIntExtra("id_user", 0);
        txtUserName.setText("Xin chào " + user_name);
        User u = getUserById(id_user);
        edtTaiKhoan.setText(u.getName());
        edtMatKhau.setText(u.getPassword());
        if (u.getRole() == 1)
            rdoAdmin.setChecked(true);
        else rdoUser.setChecked(true);

    }

    private boolean updateUser(User user) {
        Database db = new Database(this);
        return db.updateUser(user);
    }

    private boolean validateInput() {
        String matKhau = edtMatKhau.getText().toString().trim();

        if (matKhau.isEmpty()) {
            Toast.makeText(EditUserActivity.this, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (matKhau.length() < 3) {
            Toast.makeText(EditUserActivity.this, "Mật khẩu phải có ít nhất 3 kí tự!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private User getUserById(int id) {
        Database db = new Database(this);
        return db.getUserById(id);
    }

    private void setEditTextError(EditText editText, String error) {
        editText.requestFocus();
        editText.setError(error);
    }

}
