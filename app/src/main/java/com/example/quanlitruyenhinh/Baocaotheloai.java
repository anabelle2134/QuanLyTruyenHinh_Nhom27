package com.example.quanlitruyenhinh;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlitruyenhinh.CSDLTruyenHinh;

import java.util.ArrayList;

public class Baocaotheloai extends AppCompatActivity {
    TextView tvtl, tvmtl;
    CSDLTruyenHinh database;
    ArrayList<String> dataTheloai = new ArrayList<String>();
    TableLayout table;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaotheloai);
        setEvent();
    }

    private void setEvent() {
        String msg = getIntent().getStringExtra("tl");
        tvtl = findViewById(R.id.tv_TenTL);
        tvtl.setText(msg);
        database = new CSDLTruyenHinh(this, "dbQLTH", null, 1);
        Cursor data = database.getData("SELECT MATL FROM THELOAI WHERE TENTL = '" + msg + "'");
        tvmtl = findViewById(R.id.tv_MaTL);
        Cursor dataTL = null;

        while (data.moveToNext()) {
            tvmtl.setText(data.getString(0));
            dataTL = database.getData("SELECT CHUONGTRINH.MACT, CHUONGTRINH.TENCT,THONGTINPHATSONG.THOILUONG FROM THONGTINPHATSONG , CHUONGTRINH   WHERE   THONGTINPHATSONG.MACT = CHUONGTRINH.MACT AND CHUONGTRINH.MATL = '" +data.getString(0)+ "'");
        }

        while (dataTL.moveToNext()) {
            for (int i = 0; i < 3; i++) {
                //Toast.makeText(Baocaotheloai.this, dataTL.getString(i), Toast.LENGTH_SHORT).show();
                dataTheloai.add(dataTL.getString(i));
            }

        }

        TableLayout table = (TableLayout) findViewById(R.id.table_TheLoai);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        int paddingDp = 10;
        float density = tbrow0.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);
        tbrow0.setPadding(0,paddingPixel,0,0);
        tv0.setText(" STT ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Mã chương trình ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Tên Chương trình  ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("   Thời lượng ");
        tv3.setTextColor(Color.WHITE);

        tbrow0.addView(tv3);
        table.addView(tbrow0);

        for (int i = 0; i < dataTheloai.size(); i+=3) {

            for (int j=i;j<i+3;j+=3) {
                int k = 1;
                String data1 = dataTheloai.get(j);
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText("" + k);
                t1v.setTextColor(Color.WHITE);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(dataTheloai.get(j));
                t2v.setTextColor(Color.WHITE);
                t2v.setGravity(Gravity.CENTER);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(dataTheloai.get(j+1));
                t3v.setTextColor(Color.WHITE);
                t3v.setGravity(Gravity.CENTER);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText( dataTheloai.get(j+2));
                t4v.setTextColor(Color.WHITE);
                t4v.setGravity(Gravity.CENTER);
                tbrow.addView(t4v);
                table.addView(tbrow);
                k++;
            }

        }
    }
}