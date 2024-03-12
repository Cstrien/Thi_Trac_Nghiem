package com.cstrien.thi_trac_nghiem.admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.LoginActivity;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.adapter.UserAdapter;
import com.cstrien.thi_trac_nghiem.model.User;

import java.util.ArrayList;

public class MenuUserActivity extends AppCompatActivity {
    // Khai báo các biến và thành phần giao diện
    private TextView txtUserName;
    private TextView btnHome;
    private TextView btnClear;
    private EditText edtSearch;
    private ListView lvUser;
    private ImageButton btnAddUser;
    private String user_name;
    private long mLastClickTime = 0;
    private ArrayList<User> listUsers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        // Khởi tạo các thành phần giao diện
        addControls();

        // Load danh sách người dùng
        loadUsers(null);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        txtUserName.setText("Xin chào " + user_name);

        // Xử lý sự kiện khi nhấn nút "Thêm người dùng"
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUserActivity.this, AddUserActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Trang chính"
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUserActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn lâu vào một người dùng để xóa
        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogDelete(position);
                return false;
            }
        });

        // Xử lý sự kiện khi nhấn vào một người dùng để chỉnh sửa
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long currTime = System.currentTimeMillis();
                if (currTime - mLastClickTime < ViewConfiguration.getDoubleTapTimeout()) {
                    onItemDoubleClick(parent, view, position, id);
                }
                mLastClickTime = currTime;
            }

            public void onItemDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MenuUserActivity.this, EditUserActivity.class);
                int id_user = listUsers.get(position).getId();
                intent.putExtra("user", user_name);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi người dùng thay đổi nội dung tìm kiếm
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = edtSearch.getText().toString().trim();
                if (!search.equalsIgnoreCase("")) {
                    loadUsers(search);
                } else {
                    loadUsers(null);
                }
                if (edtSearch.getText().toString().equalsIgnoreCase("")) {
                    btnClear.setBackgroundResource(R.drawable.search);
                } else {
                    btnClear.setBackgroundResource(R.drawable.clear);
                    btnClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edtSearch.setText("");
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Hiển thị dialog xác nhận xóa người dùng
    private void DialogDelete(int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.setCanceledOnTouchOutside(false);

        TextView btnYes = dialog.findViewById(R.id.btnYes);
        TextView btnNo = dialog.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_user = listUsers.get(position).getId();
                User user = listUsers.get(position);
                if (deleteUser(id_user)) {
                    loadUsers(null);
                    dialog.cancel();
                    Toast.makeText(MenuUserActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    if(user.getName().equalsIgnoreCase(user_name)){
                        Intent intent = new Intent(MenuUserActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else
                    Toast.makeText(MenuUserActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    // Xóa người dùng từ cơ sở dữ liệu
    private boolean deleteUser(int id_user) {
        Database db = new Database(this);
        return db.deleteUser(id_user);
    }

    // Khởi tạo các thành phần giao diện
    private void addControls() {
        txtUserName = findViewById(R.id.txtUserName);
        btnAddUser = findViewById(R.id.btnAddUser);
        txtUserName = findViewById(R.id.txtUserName);
        btnHome = findViewById(R.id.btnHome);
        lvUser = findViewById(R.id.lvUser);
        edtSearch = findViewById(R.id.edtSearch);
        btnClear = findViewById(R.id.btnClear);
    }

    // Tải danh sách người dùng từ cơ sở dữ liệu
    private void loadUsers(String key) {
        Database db = new Database(this);
        listUsers = db.getListUsers(key);
        UserAdapter userAdapter = new UserAdapter(listUsers);
        lvUser.setAdapter(userAdapter);
    }

}
