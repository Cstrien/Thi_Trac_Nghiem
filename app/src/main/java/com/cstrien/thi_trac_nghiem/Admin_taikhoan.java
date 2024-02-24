package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cstrien.thi_trac_nghiem.adapter.TaiKhoanAdapter;
import com.cstrien.thi_trac_nghiem.model.TaiKhoan;

import java.util.ArrayList;

public class Admin_taikhoan extends AppCompatActivity {
    ListView lvTaiKhoan;
    ArrayList<TaiKhoan>dsTaiKhoan=new ArrayList<>();
    TaiKhoanAdapter taiKhoanAdapter;
    //Tạo một list để lưu vị trí click Checbok để xóa nhân viên
    //phải public và do ở hàm Admin_taikhoan nên phải dùng sâttic
    public static ArrayList<Integer>vitri =new ArrayList<>();

    Button btn_Xoa,btn_Them;
    EditText edt_ID,edt_Name;
    RadioButton rdo_btnNam;
    RadioButton rdo_btnNu;
    boolean gioitinh=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_taikhoan);
        addControls();
        addEvent();
    }

    private void addEvent() {
        //sự kiện xóa


        btn_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vitri.isEmpty()){
                    for( int k:vitri){
                        dsTaiKhoan.remove(k);
                    }
                    vitri.clear();
                    taiKhoanAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(Admin_taikhoan.this,"Chưa chọn tài khoản xóa",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        if(rdo_btnNam.isChecked()){
            gioitinh=true;

        }else {
            gioitinh=false;
        }
        //sự kiện thêm
        btn_Them.setOnClickListener(new View.OnClickListener() {
            boolean gioitinh=true;

            @Override
            public void onClick(View v) {
                dsTaiKhoan.add(new TaiKhoan(edt_ID.getText().toString(),edt_Name.getText().toString(),gioitinh));

            }
        });
    }


    private void addControls() {

        btn_Xoa=findViewById(R.id.btnXoa);
        btn_Them=findViewById(R.id.btnThem);
        edt_ID=findViewById(R.id.edtID);
        edt_Name=findViewById(R.id.edtName);
        rdo_btnNam=findViewById(R.id.radioBtnNam);
        rdo_btnNu=findViewById(R.id.radioBtnNu);


        lvTaiKhoan=findViewById(R.id.dsHienthi);
        dsTaiKhoan.add(new TaiKhoan("NV1","Lê ",false));
        dsTaiKhoan.add(new TaiKhoan("NV2"," Nam",true));
        dsTaiKhoan.add(new TaiKhoan("NV3","Mai ",false));
        dsTaiKhoan.add(new TaiKhoan("NV4","Cường ",true));
        dsTaiKhoan.add(new TaiKhoan("NV5","Bình ",true));
        dsTaiKhoan.add(new TaiKhoan("NV6","Triển ",true));
        dsTaiKhoan.add(new TaiKhoan("NV7","BEn ",true));
        dsTaiKhoan.add(new TaiKhoan("NV8","Nhân ",true));
        taiKhoanAdapter=new TaiKhoanAdapter(
                Admin_taikhoan.this,R.layout.items_admin_taikhoan,
                dsTaiKhoan
        );
        lvTaiKhoan.setAdapter(taiKhoanAdapter);

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar=getSupportActionBar() ;
        actionBar.setTitle("Quản lý tài khoản");

        return super.onCreateOptionsMenu(menu);
    }


}