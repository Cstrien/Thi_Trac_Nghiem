package com.cstrien.thi_trac_nghiem.admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cstrien.thi_trac_nghiem.Database;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.adapter.QuestionAdapter;
import com.cstrien.thi_trac_nghiem.LoginActivity;
import com.cstrien.thi_trac_nghiem.user.MainActivity;
import com.cstrien.thi_trac_nghiem.model.Category;
import com.cstrien.thi_trac_nghiem.model.Question;

import java.util.ArrayList;

public class MenuQuestionActivity extends AppCompatActivity {
    private TextView txtUserName;

    private TextView btnHome;
    private TextView btnClear;
    private EditText edtSearch;
    private ImageButton btnAddQuestion;
    private Spinner spCategory;
    private ListView lvQuestion;
    private ArrayList<Question> listQuestion;
    private ArrayList<Category> listCategories;
    private QuestionAdapter questionAdapter;
    private String user_name;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_question);
        //
        anhXa();
        //
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        txtUserName.setText("Xin chào " + user_name);
        //load danh sách câu hỏi theo chủ đề
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuQuestionActivity.this, AddQuestionActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        //sự kiện nhấn vào item listView
        lvQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long currTime = System.currentTimeMillis();
                if (currTime - mLastClickTime < ViewConfiguration.getDoubleTapTimeout()) {
                    onItemDoubleClick(parent, view, position, id);
                }
                mLastClickTime = currTime;
            }

            public void onItemDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MenuQuestionActivity.this, EditQuestionActivity.class);
                int id_question = listQuestion.get(position).getId();
                intent.putExtra("user", user_name);
                intent.putExtra("id_question", id_question);
                startActivity(intent);
            }
        });
        lvQuestion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogDelete(position);
                return false;
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuQuestionActivity.this, AdminActivity.class);
                intent.putExtra("user", user_name);
                startActivity(intent);
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = edtSearch.getText().toString().trim();
                if (!search.equalsIgnoreCase("")) {
                    loadSearch(search);
                } else {
                    loadListView();
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
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadSearch(String name) {
        Category category = (Category) spCategory.getSelectedItem();
        int id_cate = category.getId();
        // Lấy dữ liệu câu hỏi theo chủ đề
        Database db = new Database(this);
        listQuestion = db.getListQuestions(name, id_cate);
        listCategories = db.getListCategories(null);
        questionAdapter = new QuestionAdapter(listQuestion, listCategories);
        lvQuestion.setAdapter(questionAdapter);
    }

    private void loadListView() {
        Category category = (Category) spCategory.getSelectedItem();
        int id_cate = category.getId();
        // Lấy dữ liệu câu hỏi theo chủ đề
        Database db = new Database(this);
        listQuestion = db.getListQuestions(null, id_cate);
        listCategories = db.getListCategories(null);
        questionAdapter = new QuestionAdapter(listQuestion, listCategories);
        lvQuestion.setAdapter(questionAdapter);
    }

    private void anhXa() {
        txtUserName = findViewById(R.id.txtUserName);
        lvQuestion = findViewById(R.id.lvQuestion);
        spCategory = findViewById(R.id.spCategory);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnHome = findViewById(R.id.btnHome);
        btnClear = findViewById(R.id.btnClear);

        edtSearch = findViewById(R.id.edtSearch);

        loadCategories();
        loadListView();
    }

    private void loadCategories() {
        Database db = new Database(this);
        ArrayList<Category> listCate = db.getListCategories(null);
        // tạo adapter
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCate);
        // bố cục
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // gắn chủ đề lên spinner hiển thị
        spCategory.setAdapter(categoryArrayAdapter);
    }

    private void DialogDelete(int position) {
        //tạo đối tượng dialog
        Dialog dialog = new Dialog(this);

        //nạp layout vào dialog
        dialog.setContentView(R.layout.dialog_delete);

        //Tăt click ra ngoài chỉ click vào NO là đóng
        dialog.setCanceledOnTouchOutside(false);

        TextView btnYes = dialog.findViewById(R.id.btnYes);
        TextView btnNo = dialog.findViewById(R.id.btnNo);

        //set sự kiện click vào yes
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_question = listQuestion.get(position).getId();
                //xóa dữ liệu
                if (deleteQuestion(id_question)) {
                    loadListView();
                    dialog.cancel();
                    Toast.makeText(MenuQuestionActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MenuQuestionActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
            }
        });

        //set sự kiện click NO
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //thực hiện dialog
        dialog.show();
    }

    private boolean deleteQuestion(int id_question) {
        Database db = new Database(this);
        return db.deleteQuestion(id_question);
    }


}
