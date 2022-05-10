package com.example.quanlitruyenhinh;

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

public class BaocaoChuongTrinh extends AppCompatActivity {
    TextView tvct, tvmct;
    CSDLTruyenHinh database;
    ArrayList<String> dataChuongTrinh = new ArrayList<String>();
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocao_chuong_trinh);
        setEvent();
    }

    private void setEvent() {
        String msg = getIntent().getStringExtra("ct");
        tvct = findViewById(R.id.tv_TenCT);
        tvct.setText(msg);
        database = new CSDLTruyenHinh(this, "dbQLTH", null, 1);
        Cursor data = database.getData("SELECT MACT FROM CHUONGTRINH WHERE TENCT = '" + msg + "'");
        tvmct = findViewById(R.id.tv_MaCT);


        Cursor dataCT = null;
        while (data.moveToNext()) {
            tvmct.setText(data.getString(0));
            dataCT = database.getData("SELECT THONGTINPHATSONG.MABTV , BIENTAPVIEN.HOTEN, THONGTINPHATSONG.NGAYPS, THONGTINPHATSONG.THOILUONG \n" +
                    "FROM THONGTINPHATSONG , BIENTAPVIEN   WHERE THONGTINPHATSONG.MABTV = BIENTAPVIEN.MABTV AND THONGTINPHATSONG.MACT= '" + data.getString(0) + "'");

        }

        while (dataCT.moveToNext()) {
            for (int i = 0; i < 4; i++) {
                // Toast.makeText(BaocaoChuongTrinh.this, dataCT.getString(i), Toast.LENGTH_SHORT).show();
                dataChuongTrinh.add(dataCT.getString(i));

            }

        }
        TableLayout table = (TableLayout) findViewById(R.id.table_ChuongTrinh);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" STT ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Mã biên tập viên ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" họ tên ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText("   Ngày phát sóng ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText("   Thời lượng ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);

        table.addView(tbrow0);
        for (int i = 0; i < dataChuongTrinh.size(); i+=4) {
            int k =  0;
            for (int j=i;j<i+4;j+=4) {

                String data1 = dataChuongTrinh.get(j);
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText("" + k);
                t1v.setTextColor(Color.WHITE);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(dataChuongTrinh.get(j));
                t2v.setTextColor(Color.WHITE);
                t2v.setGravity(Gravity.CENTER);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(dataChuongTrinh.get(j+1));
                t3v.setTextColor(Color.WHITE);
                t3v.setGravity(Gravity.CENTER);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText( dataChuongTrinh.get(j+2));
                t4v.setTextColor(Color.WHITE);
                t4v.setGravity(Gravity.CENTER);
                tbrow.addView(t4v);
                TextView t5v = new TextView(this);
                t5v.setText( dataChuongTrinh.get(j+3));
                t5v.setTextColor(Color.WHITE);
                t5v.setGravity(Gravity.CENTER);
                tbrow.addView(t5v);
                table.addView(tbrow);
            }
            k++;
        }

    }
}