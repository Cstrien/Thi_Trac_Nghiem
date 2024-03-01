package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cstrien.thi_trac_nghiem.model.Sinhvien;
import com.cstrien.thi_trac_nghiem.MainActivity;

import java.io.File;
import java.util.ArrayList;

public class Admin_taikhoan extends AppCompatActivity {
    private static final String DATABASE_NAME = "THI_TRAC_NGHIEM_DATA";
    ListView lvTaiKhoan;
    ArrayAdapter<Sinhvien> arrayAdapterSinhvien;
    Button btn_Xoa, btn_Them;
    EditText edt_Username, edt_Password, edt_Mssv, edt_TenSV;
    ImageView img_Back;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_taikhoan);
        addControls();
        createDatabase();
        loadData();
        addEvent();
    }

    private void addControls() {
        btn_Xoa = findViewById(R.id.btnXoa);
        btn_Them = findViewById(R.id.btnThem);
        edt_Mssv = findViewById(R.id.edtMssv);
        edt_TenSV = findViewById(R.id.edtTenSV);
        edt_Username = findViewById(R.id.edtUsername);
        edt_Password = findViewById(R.id.edtPassword);
        img_Back = findViewById(R.id.imgBack);
        lvTaiKhoan = findViewById(R.id.lvTaikhoan);
        arrayAdapterSinhvien = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        lvTaiKhoan.setAdapter(arrayAdapterSinhvien);
    }

    private void loadData() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from SinhVien", null);
        arrayAdapterSinhvien.clear();
        while (cursor.moveToNext()) {
            String SinhvienID = cursor.getString(0);
            String TenSV = cursor.getString(1);
            String Username = cursor.getString(2);
            String Password = cursor.getString(3);
            Sinhvien u = new Sinhvien(SinhvienID, TenSV, Username, Password);
            arrayAdapterSinhvien.add(u);
        }
        cursor.close();
    }

    private void addEvent() {
        btn_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa tài khoản được chọn từ cơ sở dữ liệu
                int kq = database.delete("SinhVien", "SinhvienID=?", new String[]{edt_Mssv.getText().toString()});
                if (kq > 0) {
                    // Nếu xóa thành công, cập nhật lại danh sách và ListView
                    loadData();
                    arrayAdapterSinhvien.notifyDataSetChanged();
                    Toast.makeText(Admin_taikhoan.this, "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Admin_taikhoan.this, "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thêm tài khoản mới vào cơ sở dữ liệu
                ContentValues values = new ContentValues();
                values.put("SinhvienID", edt_Mssv.getText().toString());
                values.put("TenSV", edt_TenSV.getText().toString());
                values.put("Username", edt_Username.getText().toString());
                values.put("Password", edt_Password.getText().toString());
                long kq = database.insert("SinhVien", null, values);
                if (kq > 0) {
                    // Nếu thêm thành công, cập nhật lại danh sách và ListView
                    loadData();
                    arrayAdapterSinhvien.notifyDataSetChanged();
                    Toast.makeText(Admin_taikhoan.this, "Đã thêm tài khoản", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Admin_taikhoan.this, "Thêm tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_taikhoan.this, Admin.class);
                startActivity(intent);
            }
        });

        lvTaiKhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy ra tài khoản được chọn từ danh sách
                final Sinhvien selectedSinhvien = arrayAdapterSinhvien.getItem(position);

                // Tạo một dialog để chỉnh sửa thông tin của tài khoản
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_taikhoan.this);
                builder.setTitle("Chỉnh sửa tài khoản");

                // Inflate layout cho dialog
                View dialogView = getLayoutInflater().inflate(R.layout.activity_update_admin_taikhoan, null);
                builder.setView(dialogView);

                // Khởi tạo các EditText và đặt giá trị ban đầu
                final EditText edtTenSV = dialogView.findViewById(R.id.edtTenSVUpdate);
                final EditText edtUsername = dialogView.findViewById(R.id.edtUsernameUpdate);
                final EditText edtPassword = dialogView.findViewById(R.id.edtPasswordUpdate);

                edtTenSV.setText(selectedSinhvien.getTenSV());
                edtUsername.setText(selectedSinhvien.getUsername());
                edtPassword.setText(selectedSinhvien.getPassword());



                // Xử lý sự kiện cho nút Lưu trong dialog
                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy giá trị mới từ các EditText
                        String newTenSV = edtTenSV.getText().toString();
                        String newUsername = edtUsername.getText().toString();
                        String newPassword = edtPassword.getText().toString();

                        // Cập nhật thông tin của tài khoản trong cơ sở dữ liệu
                        ContentValues values = new ContentValues();
                        values.put("TenSV", newTenSV);
                        values.put("Username", newUsername);
                        values.put("Password", newPassword);

                        int result = database.update("SinhVien", values, "SinhvienID=?", new String[]{selectedSinhvien.getSinhvienID()});
                        if (result > 0) {
                            // Nếu cập nhật thành công, thông báo và cập nhật lại ListView
                            Toast.makeText(Admin_taikhoan.this, "Thông tin tài khoản đã được cập nhật", Toast.LENGTH_SHORT).show();
                            loadData(); // Cập nhật lại danh sách tài khoản từ cơ sở dữ liệu
                            arrayAdapterSinhvien.notifyDataSetChanged(); // Cập nhật lại ListView
                        } else {
                            // Nếu cập nhật thất bại, thông báo lỗi
                            Toast.makeText(Admin_taikhoan.this, "Cập nhật thông tin tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Xử lý sự kiện cho nút Hủy bỏ trong dialog
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng dialog nếu người dùng chọn Hủy bỏ
                        dialog.dismiss();
                    }
                });

                // Hiển thị dialog
                builder.create().show();
            }
        });

    }

    private void createDatabase() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS SinhVien (SinhvienID TEXT PRIMARY KEY, TenSV TEXT, Username TEXT, Password TEXT)");
    }
}






