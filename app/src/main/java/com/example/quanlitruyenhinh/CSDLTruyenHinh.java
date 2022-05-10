package com.example.quanlitruyenhinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.quanlitruyenhinh.MainActivity;
import com.example.quanlitruyenhinh.Model.BienTapVien;
import com.example.quanlitruyenhinh.Model.DanhMucChuongTrinh;
import com.example.quanlitruyenhinh.Model.ThongTinPhatSong;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSDLTruyenHinh extends SQLiteOpenHelper {

//    Context context;
//    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "QuanLiTruyenHinh.db";


    public CSDLTruyenHinh(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public void themDanhMucCT(String maChuongTrinh, String tenChuongTrinh, String maTheLoai) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO CHUONGTRINH VALUES (?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, maChuongTrinh);
        statement.bindString(2, tenChuongTrinh);
        statement.bindString(3, maTheLoai);

        statement.executeInsert();
        db.close();
    }

    public void xoaDanhMucCT(String maChuongTrinh) {
        SQLiteDatabase db = this.getWritableDatabase();
        String xoa = "DELETE FROM CHUONGTRINH WHERE MACT = '" + maChuongTrinh + "'";
        db.execSQL(xoa);
        db.close();
    }

    public void suaDanhMucCT(String tenChuongTrinh, String maChuongTrinh) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE CHUONGTRINH SET ";
        sql += "TENCT = '" + tenChuongTrinh + "'";
        sql += "WHERE MACT = '" + maChuongTrinh + "'";
        db.execSQL(sql);
        db.close();
    }

    public void themBTV(String maBTV, String tenBTV, String NamSinh, String SDT) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO BIENTAPVIEN VALUES (?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, maBTV);
        statement.bindString(2, tenBTV);
        statement.bindString(3, NamSinh);
        statement.bindString(4, SDT);
        statement.executeInsert();
        db.close();
    }

    public void xoaBTV(String maBTV) {
        SQLiteDatabase db = this.getWritableDatabase();
        String xoa = "DELETE FROM BIENTAPVIEN WHERE MABTV = '" + maBTV + "'";
        db.execSQL(xoa);
        db.close();
    }

    public void suaBTV(String tenBTV, String namSinh, String SDT, String maBTV) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE BIENTAPVIEN SET  ";
        sql += "HOTEN = '" + tenBTV + "', ";
        sql += "NAMSINH = '" + namSinh + "',  ";
        sql += "SDT = '" + SDT + "'";
        sql += " WHERE MABTV = '" + maBTV + "'";
        db.execSQL(sql);
        db.close();
    }

    public void themThongTinPhatSong(String maPhatSong, String maChuongTrinh, String maBTV, String ngayPhatSong, String thoiLuong) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO THONGTINPHATSONG VALUES (?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, maPhatSong);
        statement.bindString(2, maChuongTrinh);
        statement.bindString(3, maBTV);
        statement.bindString(4, ngayPhatSong);
        statement.bindString(5, thoiLuong);

        statement.executeInsert();
        db.close();
    }

    /*public void xoaThongTinPhatSong(ThongTinPhatSong maPhatSong){
        SQLiteDatabase db = this.getWritableDatabase();
        String xoa = "DELETE FROM THONGTINPHATSONG WHERE maPhatSong = '" +maPhatSong+"'";
        db.execSQL(xoa);
    }*/
    public void xoaThongTinPhatSong(ThongTinPhatSong ttps) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM THONGTINPHATSONG WHERE MAPS='" + ttps.getMaPhatSong() + "'";
        db.execSQL(query);
        db.close();
    }

    public void suaThongTinPhatSong(String maPhatSong, String maBTV, String maCT, String ngayPhatSong, String thoiLuong) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE THONGTINPHATSONG SET  ";
        sql += "MABTV = '" + maBTV + "', ";
        sql += "MACT = '" + maCT + "',  ";
        sql += "NGAYPS = '" + ngayPhatSong + '\'';
        sql += "THOILUONG = '" + thoiLuong + "'";
        sql += " WHERE MAPS = '" + maPhatSong + "'";
        db.execSQL(sql);
        db.close();
    }

    public void themTL(String maTL, String tenTL, String moTa) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO THELOAI VALUES (?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, maTL);
        statement.bindString(2, tenTL);
        statement.bindString(3, moTa);
        statement.executeInsert();
        db.close();
    }

    public void xoaTL(String maTL) {
        SQLiteDatabase db = this.getWritableDatabase();
        String xoa = "DELETE FROM THELOAI WHERE MATL = '" + maTL + "'";
        db.execSQL(xoa);
        db.close();
    }

    public void suaTL(String tenTL, String moTa, String maTL) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE THELOAI SET  ";
        sql += "TENTL = '" + tenTL + "', ";
        sql += "MOTA = '" + moTa + "'";
        sql += " WHERE MATL = '" + maTL + "'";

        db.execSQL(sql);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE THELOAI(MATL nvarchar(30) PRIMARY KEY, TENTL nvarchar(50), MOTA nvarchar(30))");
//        db.execSQL("CREATE TABLE CHUONGTRINH(MACT nvarchar(30) PRIMARY KEY, TENCT nvarchar(50), MATL nvarchar(30))");
//        db.execSQL("CREATE TABLE BIENTAPVIEN(MABTV nvarchar(30) PRIMARY KEY, HOTEN nvarchar(50), NAMSINH nvarchar(10), SDT nvarchar(10))");
//        db.execSQL("CREATE TABLE THONGTINPHATSONG(MAPS nvarchar(30) PRIMARY KEY,MABTV nvarchar(30)," +
//                "MACT nvarchar(30), NGAYPS nvarchar(30), THOILUONG nvarchar(10))");
       //  db = getWritableDatabase();

        db.execSQL("Create Table IF NOT EXISTS BIENTAPVIEN (MABTV NAVARCHAR(50) PRIMARY KEY,HOTEN NAVARCHAR(50), NAMSINH NAVARCHAR(10),SODT NAVARCHAR(20))");
        db.execSQL("Create Table IF NOT EXISTS THONGTINPHATSONG (MAPS NAVARCHAR(50) PRIMARY KEY,MACT NAVARCHAR(50), MABTV NAVARCHAR(50),NGAYPS NAVARCHAR(20), THOILUONG INT)");
        db.execSQL("Create Table IF NOT EXISTS CHUONGTRINH (MACT NAVARCHAR(50), TENCT NAVARCHAR(1000), MATL NAVARCHAR(50) )");
        db.execSQL("Create Table IF NOT EXISTS THELOAI(MATL NAVARCHAR(50), TENTL NAVARCHAR(100),MOTA nvarchar(30) )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS CHUONGTRINH");
//        db.execSQL("DROP TABLE IF EXISTS BIENTAPVIEN");
//        db.execSQL("DROP TABLE IF EXISTS THELOAI");
//        db.execSQL("DROP TABLE IF EXISTS THONGTINPHATSONG");
//        onCreate(db);
//        db.close();
    }
}
