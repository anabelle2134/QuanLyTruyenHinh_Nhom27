package com.example.quanlitruyenhinh.Model;

public class DanhMucChuongTrinh {
    String maChuongTrinh;
    String tenChuongTrinh;
    String maTheLoai;
    public DanhMucChuongTrinh(String maChuongTrinh) {
        this.maChuongTrinh = maChuongTrinh;
    }

    public DanhMucChuongTrinh() {
    }

    public DanhMucChuongTrinh(String maChuongTrinh, String tenChuongTrinh, String maTheLoai) {
        this.maChuongTrinh = maChuongTrinh;
        this.tenChuongTrinh = tenChuongTrinh;
        this.maTheLoai = maTheLoai;
    }

    public String getMaChuongTrinh() {
        return maChuongTrinh;
    }

    public void setMaChuongTrinh(String maChuongTrinh) {
        this.maChuongTrinh = maChuongTrinh;
    }

    public String getTenChuongTrinh() {
        return tenChuongTrinh;
    }

    public void setTenChuongTrinh(String tenChuongTrinh) {
        this.tenChuongTrinh = tenChuongTrinh;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }
}
