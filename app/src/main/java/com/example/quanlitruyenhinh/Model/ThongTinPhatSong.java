package com.example.quanlitruyenhinh.Model;

public class ThongTinPhatSong {

    String maPhatSong;
    String maBTV;
    String maChuongTrinh;
    String thoiLuong;
    String ngayPhatSong;

    public ThongTinPhatSong() {
    }

    public ThongTinPhatSong(String maPhatSong, String maBTV, String maChuongTrinh, String thoiLuong, String ngayPhatSong) {
        this.maPhatSong = maPhatSong;
        this.maBTV = maBTV;
        this.maChuongTrinh = maChuongTrinh;
        this.thoiLuong = thoiLuong;
        this.ngayPhatSong = ngayPhatSong;
    }

    public String getMaPhatSong() {
        return maPhatSong;
    }

    public void setMaPhatSong(String maPhatSong) {
        this.maPhatSong = maPhatSong;
    }

    public String getMaBTV() {
        return maBTV;
    }

    public void setMaBTV(String maBTV) {
        this.maBTV = maBTV;
    }

    public String getMaChuongTrinh() {
        return maChuongTrinh;
    }

    public void setMaChuongTrinh(String maChuongTrinh) {
        this.maChuongTrinh = maChuongTrinh;
    }

    public String getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(String thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getNgayPhatSong() {
        return ngayPhatSong;
    }

    public void setNgayPhatSong(String ngayPhatSong) {
        this.ngayPhatSong = ngayPhatSong;
    }
}