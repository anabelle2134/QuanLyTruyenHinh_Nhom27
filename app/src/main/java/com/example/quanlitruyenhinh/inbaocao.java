package com.example.quanlitruyenhinh;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class inbaocao extends AppCompatActivity {
    Button btnback, btnprint;
    CheckBox cbtl, cbct;
    CSDLTruyenHinh database;
    Spinner spinerCategorytl, spinerCategoryct;
    CategoryAdapterTheLoai categoryAdapterTheLoai;
    CategoryAdapterChuongTrinh categoryAdapterChuongTrinh;
    Intent intentTL, intentCT;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbaocao);
        setControl();
        setEvent();
        setDatabase();
        khoitao();
    }

    private void setDatabase() {
        database = new CSDLTruyenHinh(this, "dbQLTH", null, 1);
//        database.queryData("Create Table IF NOT EXISTS BIENTAPVIEN (MABTV NAVARCHAR(50) PRIMARY KEY,HOTEN NAVARCHAR(50), NAMSINH NAVARCHAR(10),SODT NAVARCHAR(20))");
//        database.queryData("Create Table IF NOT EXISTS THONGTINPHATSONG (MAPS NAVARCHAR(50) PRIMARY KEY,MACT NAVARCHAR(50), MABTV NAVARCHAR(50),NGAYPS NAVARCHAR(20), THOILUONG INT)");
//        database.queryData("Create Table IF NOT EXISTS CHUONGTRINH (MACT NAVARCHAR(50), TENCT NAVARCHAR(1000), MATL NAVARCHAR(50) )");
//        database.queryData("Create Table IF NOT EXISTS THELOAI(MATL NAVARCHAR(50), TENTL NAVARCHAR(100) )");
//        database.queryData("INSERT INTO BIENTAPVIEN  VALUES ('BTV01', 'Nguyễn Hoài Anh', 1980, '0935844569')");
//        database.queryData("INSERT INTO BIENTAPVIEN  VALUES ('BTV02', 'Lê Quang Minh', 1978, '0914345923')");//        database.QueryData("INSERT INTO BIENTAPVIEN  VALUES ('BTV03', 'Trần khánh vũ', 1983, '0948234799')");
//        database.queryData("INSERT INTO BIENTAPVIEN  VALUES ('BTV03', 'Trần khánh vũ', 1983, '0948234799')");
//       database.queryData("INSERT INTO CHUONGTRINH VALUES ('CT05', 'Ai là triệu phú', 'TC')");
//        database.queryData("INSERT INTO CHUONGTRINH VALUES ('CT01', 'Bí mật đêm chủ nhật', 'TC')");
//        database.queryData("INSERT INTO THELOAI VALUES ('TC', 'Trò chơi truyền hình','xyz')");
//        database.queryData("INSERT INTO THELOAI VALUES ('AN', 'Âm nhạc','abc')");
//       database.queryData("INSERT INTO THONGTINPHATSONG  VALUES ('07', 'CT01', 'BTV01', '08/08/2017', 90)");
//       database.queryData("INSERT INTO THONGTINPHATSONG  VALUES ('05', 'CT05', 'BTV03', '17/07/2017', 60)");

    }

    private void setEvent() {
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbtl.isChecked()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(inbaocao.this)
                            .setIcon(R.drawable.pic123)
                            .setTitle("Thông Báo")
                            .setMessage("Bạn có muốn in không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(intentTL);
                                }
                            })
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }
                if (cbct.isChecked()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(inbaocao.this)
                            .setIcon(R.drawable.pic123)
                            .setTitle("Thông Báo")
                            .setMessage("Bạn có muốn in không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(intentCT);
                                }
                            })
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }
            }
        });

    }


    private void setControl() {

        btnback = findViewById(R.id.back);
        btnprint = findViewById(R.id.print);
        cbtl = findViewById(R.id.cbtl);
        cbct = findViewById(R.id.cbct);
        spinerCategorytl = findViewById(R.id.spn_categorytl);
        spinerCategoryct = findViewById(R.id.spn_categoryct);
    }

    private void khoitao() {
        categoryAdapterTheLoai = new CategoryAdapterTheLoai(this, R.layout.item_selected, getListcategorytheloai());
        spinerCategorytl.setAdapter(categoryAdapterTheLoai);
        spinerCategorytl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(inbaocao.this, categoryAdapterTheLoai.getItem(i).getTheloai(), Toast.LENGTH_SHORT).show();
                String msg = categoryAdapterTheLoai.getItem(i).getTheloai();
                intentTL = new Intent(inbaocao.this, Baocaotheloai.class);
                intentTL.putExtra("tl", msg);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        categoryAdapterChuongTrinh = new CategoryAdapterChuongTrinh(this, R.layout.item_selected, getListcategorychuongtrinh());
        spinerCategoryct.setAdapter(categoryAdapterChuongTrinh);
        spinerCategoryct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String msg = categoryAdapterChuongTrinh.getItem(i).getChuongtrinh();
                intentCT = new Intent(inbaocao.this,BaocaoChuongTrinh.class);
                intentCT.putExtra("ct", msg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<Categorychuongtrinh> getListcategorychuongtrinh() {
        List<Categorychuongtrinh> list = new ArrayList<>();
        Cursor data = database.getData("SELECT TENCT FROM CHUONGTRINH");
        while (data.moveToNext()) {
            list.add(new Categorychuongtrinh(data.getString(0)));
        }
        //Dữ liệu add vào để test
     //   list.add(new Categorychuongtrinh("Thời sự"));
        return list;
    }

    private List<CategoryTheloai> getListcategorytheloai() {
        List<CategoryTheloai> list = new ArrayList<>();
        Cursor data = database.getData("SELECT TENTL FROM THELOAI");
        while (data.moveToNext()) {
            list.add(new CategoryTheloai(data.getString(0)));
        }
        //Dữ liệu add vào để test
       // list.add(new CategoryTheloai("Âm nhạc"));

        return list;
    }

    public void onCheckboxClicked(View view) {
        switch (view.getId()) {
            case R.id.cbct:
                cbtl.setChecked(false);
                break;
            case R.id.cbtl:
                cbct.setChecked(false);
                break;

        }
    }
}