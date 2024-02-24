package com.cstrien.thi_trac_nghiem.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.cstrien.thi_trac_nghiem.Admin_taikhoan;
import com.cstrien.thi_trac_nghiem.R;
import com.cstrien.thi_trac_nghiem.model.TaiKhoan;

import java.util.List;


public class TaiKhoanAdapter extends ArrayAdapter<TaiKhoan> {
    //Màn hình sử dụng layout này
    // layout này cho từng dòng muốn hiển thị(item)

    Activity context;
    int resource;
    //Dánh sách nguồn dữ liệu muốn hiển thị lên màn hình
    @NonNull List<TaiKhoan>objects;
    public TaiKhoanAdapter(@NonNull Activity context, int resource, @NonNull List<TaiKhoan> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.context=context;
        this.resource=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View row, @NonNull ViewGroup parent) {
        // đây là một lớp để build layoyt bình tường thành code java mà android sử dụng được
        //load file xml vào hệ thống và build thành code để chương trình cso thể  sử dụng
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        // đối số this.resoure chính là item.xml ta truyền vào khi gọi AdapterTaiKhoan
        row=layoutInflater.inflate(this.resource,null);

        ImageView _img=row.findViewById(R.id.imageView);
        TextView _Profile=row.findViewById(R.id.profile);
        CheckBox _checkbox=row.findViewById(R.id.checkBox1);

        TaiKhoan taiKhoan=this.objects.get(position);
        if(taiKhoan.getGioitinh()==true) {
            _img.setImageResource(R.drawable.male);
        }
        else {
            _img.setImageResource(R.drawable.female);
        }
        _Profile.setText(taiKhoan.get_ID()+"  -  "+taiKhoan.get_Name());
        //khi nhấn checkbox sẽ lưu vị trí sang array"vitri" bên class Admin_taikhoan
        _checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(_checkbox.isChecked()){
                    //gọi arraylisst bên class Admin_taikhoan
                    //position là vị trí item (dòng thông tin)
                    Admin_taikhoan.vitri.add(position);
                    // dòng này để refresh lại vị trí click checkbox khi nhân checkbox,
                    // tiếp tục nhân sliaj checkbox lần nữa
                }else {
                    for(int x:Admin_taikhoan.vitri){
                        if(x==position){
                            Admin_taikhoan.vitri.remove(x);
                        }
                    }
                }
            }
        });

        return row;
    }
}


