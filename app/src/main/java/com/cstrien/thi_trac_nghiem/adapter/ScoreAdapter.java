package com.cstrien.thi_trac_nghiem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.model.Category;
import com.cstrien.thi_trac_nghiem.model.Score;
import com.cstrien.thi_trac_nghiem.model.User;

import java.util.ArrayList;

public class ScoreAdapter extends BaseAdapter {
    private ArrayList<Score> listScore;
    private ArrayList<Category> listCategories;
    private ArrayList<User> listUsers;

    // Constructor nhận vào danh sách điểm số, danh sách danh mục và danh sách người dùng
    public ScoreAdapter(ArrayList<Score> listScore, ArrayList<Category> listCategories, ArrayList<User> listUsers) {
        this.listScore = listScore;
        this.listCategories = listCategories;
        this.listUsers = listUsers;
    }

    // Trả về số lượng điểm số trong danh sách
    @Override
    public int getCount() {
        return listScore.size();
    }

    // Trả về điểm số tại vị trí cụ thể trong danh sách
    @Override
    public Object getItem(int position) {
        return listScore.get(position);
    }

    // Trả về ID của điểm số tại vị trí cụ thể trong danh sách
    @Override
    public long getItemId(int position) {
        return listScore.get(position).getId();
    }

    // Tạo và trả về view cho mỗi điểm số
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewScore;
        if (convertView == null) {
            // Nếu convertView là null, tạo mới view từ layout rank_view
            viewScore = View.inflate(parent.getContext(), R.layout.rank_view, null);
        } else {
            // Nếu convertView không null, sử dụng lại view đã tồn tại
            viewScore = convertView;
        }

        // Gắn dữ liệu của điểm số vào view
        Score score = (Score) getItem(position);
        ((TextView) viewScore.findViewById(R.id.name_category)).setText(String.format("%s", getNameCategory(score.getId_category())));
        ((TextView) viewScore.findViewById(R.id.user_name)).setText(String.format("%s", getNameUser(score.getId_user())));

        return viewScore;
    }

    // Phương thức helper để lấy tên của danh mục dựa trên ID
    private String getNameCategory(int id) {
        for (Category c : listCategories) {
            if (c.getId() == id) return c.getName();
        }
        return "";
    }

    // Phương thức helper để lấy tên của người dùng dựa trên ID
    private String getNameUser(int id) {
        for (User u : listUsers) {
            if (u.getId() == id) return u.getName();
        }
        return "";
    }
}
