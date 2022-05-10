package com.example.quanlitruyenhinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnexit,btninbaocao;
    Dialog thoat;
    Button thoatBtn_yes, thoatBtn_no;
    CSDLTruyenHinh database;
    private Button btnXemDanhMucCT, btnBienTapVien, btnThongTinPhatSong, btnTheLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
        setExitDialog();

    }


    private void setExitDialog(){
        thoat = new Dialog(this);
        thoat.setContentView(R.layout.thoat);
        thoat.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        thoat.setCancelable(false);
        thoatBtn_yes =thoat.findViewById(R.id.thoatBtn_yes);
        thoatBtn_no =thoat.findViewById(R.id.thoatBtn_no);

        thoatBtn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finishAffinity();
                System.exit(0);
            }
        });

        thoatBtn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thoat.dismiss();
            }
        });
    }
    private void setEvent() {

        btnXemDanhMucCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Activity_DanhMucChuongTrinh.class ));
            }
        });
        btnBienTapVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Activity_BienTapVien.class));
            }
        });
        btnThongTinPhatSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Activity_ThongTinPhatSong.class));
            }
        });
        btnTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity_TheLoai.class));
            }
        });
        btninbaocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, inbaocao.class));
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });


    }

    private void setControl() {
        btninbaocao = findViewById(R.id.btninbaocac);
        btnTheLoai=(Button) findViewById(R.id.btnTheLoai);
        btnThongTinPhatSong = (Button) findViewById(R.id.btnThongTinPhatSong) ;
        btnBienTapVien = (Button) findViewById(R.id.btnBienTapVien) ;
        btnXemDanhMucCT = (Button) findViewById(R.id.btnxemDanhMucCT);
        btnexit= findViewById(R.id.btnexit);
        // cài đặt tiêu đề cho action bar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();

    }

}