package com.cstrien.thi_trac_nghiem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.model.Category;
import com.cstrien.thi_trac_nghiem.model.Question;

import java.util.ArrayList;

public class QuestionAdapter extends BaseAdapter {
    // Danh sách các câu hỏi và danh mục
    private final ArrayList<Question> listQuestion;
    private final ArrayList<Category> listCategories;

    // Constructor nhận vào danh sách câu hỏi và danh mục
    public QuestionAdapter(ArrayList<Question> listQuestion, ArrayList<Category> listCategories) {
        this.listQuestion = listQuestion;
        this.listCategories = listCategories;
    }

    // Trả về số lượng câu hỏi trong danh sách
    @Override
    public int getCount() {
        return listQuestion.size();
    }

    // Trả về câu hỏi tại vị trí cụ thể trong danh sách
    @Override
    public Object getItem(int position) {
        return listQuestion.get(position);
    }

    // Trả về ID của câu hỏi tại vị trí cụ thể trong danh sách
    @Override
    public long getItemId(int position) {
        return listQuestion.get(position).getId();
    }

    // Tạo và trả về view cho mỗi câu hỏi
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewQuestion;
        if (convertView == null) {
            // Nếu convertView là null, tạo mới view từ layout question_view
            viewQuestion = View.inflate(parent.getContext(), R.layout.question_view, null);
        } else {
            // Nếu convertView không null, sử dụng lại view đã tồn tại
            viewQuestion = convertView;
        }

        // Gắn dữ liệu của câu hỏi vào view
        Question question = (Question) getItem(position);
        ((TextView) viewQuestion.findViewById(R.id.id_question)).setText(String.format("%d", position + 1));
        ((TextView) viewQuestion.findViewById(R.id.name_question)).setText(String.format("%s", question.getQuestion()));
        ((TextView) viewQuestion.findViewById(R.id.name_category)).setText(String.format("%s", getNameCategory(question.getId_category())));

        return viewQuestion;
    }

    // Phương thức helper để lấy tên của danh mục dựa trên ID
    private String getNameCategory(int id) {
        for (Category c : listCategories) {
            if (c.getId() == id) return c.getName();
        }
        return "";
    }

}
