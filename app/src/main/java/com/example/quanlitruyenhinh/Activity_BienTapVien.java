package com.example.quanlitruyenhinh;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.quanlitruyenhinh.Adapter.BienTapVienAdapter;
import com.example.quanlitruyenhinh.Adapter.DanhMucChuongTrinhAdapter;
import com.example.quanlitruyenhinh.Model.BienTapVien;
import com.example.quanlitruyenhinh.Model.DanhMucChuongTrinh;
import com.example.quanlitruyenhinh.CSDLTruyenHinh;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Activity_BienTapVien extends AppCompatActivity {
    ListView lvBTV;
    ArrayList <BienTapVien> data = new ArrayList<>();
    BienTapVienAdapter bienTapVienAdapter;
    FloatingActionButton floatThem;
    CSDLTruyenHinh db;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bientapvien);

        setControl();
        setEvent();
        LoadData();

    }
    public void LoadData(){
        CSDLTruyenHinh db = new CSDLTruyenHinh(this, "dbQLTH", null, 1);
        Cursor cursor = db.getData("SELECT * FROM BIENTAPVIEN");
        data.clear();
        while (cursor.moveToNext()){
            String tenBTV = cursor.getString(1);
            String maBTV = cursor.getString(0);
            String namSinh = cursor.getString(2);
            String sDT = cursor.getString(3);
            data.add(new BienTapVien(maBTV, tenBTV, namSinh, sDT));

        }
        cursor.close();
        db.close();
        bienTapVienAdapter.notifyDataSetChanged();
        lvBTV.setAdapter(bienTapVienAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timkiem_chuongtrinh, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.TimKiem).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bienTapVienAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bienTapVienAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void setEvent() {
        bienTapVienAdapter = new BienTapVienAdapter(data, this, android.R.layout.simple_list_item_1);
        lvBTV.setAdapter(bienTapVienAdapter);
        floatThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new CSDLTruyenHinh(Activity_BienTapVien.this, "dbQLTH", null, 1);
                final Dialog dialog = new Dialog(Activity_BienTapVien.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_thembientapvien);

                final EditText editThemMaBTV = (EditText) dialog.findViewById(R.id.editThemMaBTV);
                final EditText editThemTenBTV = (EditText) dialog.findViewById(R.id.editThemTenBTV);
                final EditText editThemNamSinh = (EditText) dialog.findViewById(R.id.editNamSinh);
                final EditText editThemSDT = (EditText) dialog.findViewById(R.id.editSDT);
                Button btnThemBTV = (Button) dialog.findViewById(R.id.btnThemBTV);
                Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnThemBTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maBTV = editThemMaBTV.getText().toString() ;
                        String tenBTV = editThemTenBTV.getText().toString();
                        String namSinh = editThemNamSinh.getText().toString();
                        String sdt = editThemSDT.getText().toString();
                        Boolean flagValid = true;
                        if (maBTV.isEmpty()) {
                            editThemMaBTV.setError("Mã BTV không được trống");
                            flagValid = false;
                        }
                        if (tenBTV.isEmpty()) {
                            editThemTenBTV.setError("Tên BTV không được trống");
                            flagValid = false;
                        }
                        if (namSinh.isEmpty()) {
                            editThemNamSinh.setError("Năm sinh không được trống");
                            flagValid = false;
                        }
                        if (sdt.isEmpty()) {
                            editThemSDT.setError("SĐT không được trống");
                            flagValid = false;
                        }
                        try {
                            if (flagValid){
                                db.themBTV(maBTV, tenBTV, namSinh, sdt);
                                //lvDanhMuc.setAdapter(danhMucChuongTrinhAdapter);
                                Toast.makeText(Activity_BienTapVien.this, " Thêm BTV Thành Công", Toast.LENGTH_SHORT).show();
                                LoadData();
                                dialog.dismiss();

                            }
                        }catch (Exception e){
                            Toast.makeText(Activity_BienTapVien.this, "Mã BTV Đã Tồn Tại", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        // getDataDanhMuc();
                    }
                });
                dialog.show();
            }
        });
    }

    private void setControl() {
        lvBTV = (ListView) findViewById(R.id.lvBTV);
        floatThem = (FloatingActionButton) findViewById(R.id.floatThemBTV);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.memu_back:
                Intent intent =new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
