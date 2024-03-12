package com.cstrien.thi_trac_nghiem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    // Danh sách các danh mục
    private final ArrayList<Category> lisCategory;

    // Constructor nhận vào danh sách danh mục
    public CategoryAdapter(ArrayList<Category> lisCategory) {
        this.lisCategory = lisCategory;
    }

    // Trả về số lượng danh mục trong danh sách
    @Override
    public int getCount() {
        return lisCategory.size();
    }

    // Trả về danh mục tại vị trí cụ thể trong danh sách
    @Override
    public Object getItem(int position) {
        return lisCategory.get(position);
    }

    // Trả về ID của danh mục tại vị trí cụ thể trong danh sách
    @Override
    public long getItemId(int position) {
        return lisCategory.get(position).getId();
    }

    // Tạo và trả về view cho mỗi danh mục
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCategory;
        if (convertView == null) {
            // Nếu convertView là null, tạo mới view từ layout category_view
            viewCategory = View.inflate(parent.getContext(), R.layout.category_view, null);
        } else {
            // Nếu convertView không null, sử dụng lại view đã tồn tại
            viewCategory = convertView;
        }

        // Gắn dữ liệu của danh mục vào view
        Category category = (Category) getItem(position);
        ((TextView) viewCategory.findViewById(R.id.id_category)).setText(String.format("%d", category.getId()));
        ((TextView) viewCategory.findViewById(R.id.name_category)).setText(String.format("%s", category.getName()));

        return viewCategory;
    }
}
