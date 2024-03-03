package com.cstrien.thi_trac_nghiem.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Sinhvien implements Serializable {
    private String SinhvienID;
    private String TenSV;
    private String Username;
    private String Password;



    public Sinhvien() {
    }



    public Sinhvien(String sinhvienID, String tenSV, String username, String password  ) {
        SinhvienID = sinhvienID;
        TenSV = tenSV;
        Username = username;
        Password = password;

    }

    public String getSinhvienID() {
        return SinhvienID;
    }

    public void setSinhvienID(String sinhvienID) {
        SinhvienID = sinhvienID;
    }

    public String getTenSV() {
        return TenSV;
    }

    public void setTenSV(String tenSV) {
        TenSV = tenSV;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }



    @NonNull
    @Override
    public String toString() {
        return SinhvienID+"\t"+"\t"+TenSV+"\t"+"\t"+Username+"\t"+"\t"+Password+"\t";
    }
}
