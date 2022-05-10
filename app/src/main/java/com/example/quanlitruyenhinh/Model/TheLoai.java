package com.example.quanlitruyenhinh.Model;

public class TheLoai {
    String maTL;
    String tenTL;
    String moTa;

    public TheLoai(String maTL, String tenTL, String moTa) {
        this.maTL = maTL;
        this.tenTL = tenTL;
        this.moTa = moTa;
    }

    public void setMaTL(String maTL) {
        this.maTL = maTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaTL() {
        return maTL;
    }

    public String getTenTL() {
        return tenTL;
    }

    public String getMoTa() {
        return moTa;
    }
}

