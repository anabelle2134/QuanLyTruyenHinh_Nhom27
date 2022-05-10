package com.example.quanlitruyenhinh.Model;

public class BienTapVien {
    String maBTV;
    String tenBTV;
    String namSinh;
    String sDT;

    public BienTapVien(String maBTV, String tenBTV, String namSinh, String sDT) {
        this.maBTV = maBTV;
        this.tenBTV = tenBTV;
        this.namSinh = namSinh;
        this.sDT = sDT;
    }

    public String getMaBTV() {
        return maBTV;
    }

    public void setMaBTV(String maBTV) {
        this.maBTV = maBTV;
    }

    public String getTenBTV() {
        return tenBTV;
    }

    public void setTenBTV(String tenBTV) {
        this.tenBTV = tenBTV;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }
}

