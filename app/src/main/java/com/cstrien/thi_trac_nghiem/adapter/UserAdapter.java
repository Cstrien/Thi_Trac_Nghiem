package com.cstrien.thi_trac_nghiem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.model.User;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private ArrayList<User> listUser;

    // Constructor nhận vào danh sách người dùng
    public UserAdapter(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    // Trả về số lượng người dùng trong danh sách
    @Override
    public int getCount() {
        return listUser.size();
    }

    // Trả về người dùng tại vị trí cụ thể trong danh sách
    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    // Trả về ID của người dùng tại vị trí cụ thể trong danh sách
    @Override
    public long getItemId(int position) {
        return listUser.get(position).getId();
    }

    // Tạo và trả về view cho mỗi người dùng
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewUser;
        if (convertView == null) {
            // Nếu convertView là null, tạo mới view từ layout user_view
            viewUser = View.inflate(parent.getContext(), R.layout.user_view, null);
        } else {
            // Nếu convertView không null, sử dụng lại view đã tồn tại
            viewUser = convertView;
        }

        // Gắn dữ liệu của người dùng vào view
        User user = (User) getItem(position);
        ((TextView) viewUser.findViewById(R.id.user_name)).setText(String.format("%s", user.getName()));
        ((TextView) viewUser.findViewById(R.id.password)).setText(String.format("%s", user.getPassword()));
        ((TextView) viewUser.findViewById(R.id.role)).setText(String.format("%s", getNameRole(user.getRole())));

        return viewUser;
    }

    // Phương thức helper để lấy tên vai trò dựa trên ID
    private String getNameRole(int role) {
        if (role == 1)
            return "Admin";
        return "Người dùng";
    }

}
