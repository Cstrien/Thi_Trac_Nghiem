package com.cstrien.thi_trac_nghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    public  String  DATABASE_NAME="THI_TRAC_NGHIEM_DATA";
    public String DB_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database=null;
    Button btnDangNhap,btnDangKi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proccessCopy();
        addControl();
//sự kiện login
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });


    }

    private void proccessCopy() {
            try{
                File file=getDatabasePath(DATABASE_NAME);
                if(!file.exists()) {//neu chua tao thi tao  copydatabaseFromassest
                    copyDatabaseFromAssest();
                    Toast.makeText(this,"CopyDatabase Successful",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ex)
            {
                Toast.makeText(this,"CopyDatabase Fail",Toast.LENGTH_SHORT).show();
            }

    }

    private void copyDatabaseFromAssest() {
        try{
            //mở
            InputStream inputFile=getAssets().open(DATABASE_NAME);
            //khỏi tạo để chứa
            String outputFileName=getDatabasePath();
            //mở file database
            File file=new File(getApplicationInfo().dataDir+DB_SUFFIX_PATH);
            if(!file.exists())
                file.mkdir();
            //đọc
            OutputStream outFile=new FileOutputStream(outputFileName);
            //doc mã byte
            byte[]buffer=new byte[1024];
            //chiu dai chuoi
            int length;
            //neu length đọc file nếu file mà lớn hơn 1024byte th đọc tiếp
            while ((length=inputFile.read(buffer))>0) outFile.write(buffer,0,length);
            outFile.flush();
            outFile.close();
            inputFile.close();
        }
        catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }
    public String getDatabasePath(){
        return  getApplication().getDataDir()+DB_SUFFIX_PATH+DATABASE_NAME;
    }

    private void addControl() {
        btnDangNhap=findViewById(R.id.btnDangNhap);
        btnDangKi=findViewById(R.id.btnDangKi);
    }
}