package com.example.quanlitruyenhinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlitruyenhinh.Adapter.DanhMucChuongTrinhAdapter;
import com.example.quanlitruyenhinh.Model.DanhMucChuongTrinh;
import com.example.quanlitruyenhinh.CSDLTruyenHinh;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Activity_DanhMucChuongTrinh extends AppCompatActivity {
    ListView lvDanhMuc;
    ArrayList <DanhMucChuongTrinh> data = new ArrayList<>();
    DanhMucChuongTrinhAdapter danhMucChuongTrinhAdapter;
    FloatingActionButton floatThem;
    CSDLTruyenHinh db;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_chuong_trinh);

        setControl();
        setEvent();
        LoadData();

    }
    public void LoadData(){
        CSDLTruyenHinh db = new CSDLTruyenHinh(this, "dbQLTH", null, 1);
        Cursor cursor = db.getData("SELECT * FROM CHUONGTRINH");
        data.clear();
        while (cursor.moveToNext()){
            String tenCT = cursor.getString(1);
            String maCT = cursor.getString(0);
            String maTL = cursor.getString(2);
            data.add(new DanhMucChuongTrinh(maCT, tenCT, maTL));

        }
        cursor.close();
        db.close();
        danhMucChuongTrinhAdapter.notifyDataSetChanged();
        lvDanhMuc.setAdapter(danhMucChuongTrinhAdapter);
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
                danhMucChuongTrinhAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                danhMucChuongTrinhAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void setEvent() {
        danhMucChuongTrinhAdapter = new DanhMucChuongTrinhAdapter(data, this, android.R.layout.simple_list_item_1);
        lvDanhMuc.setAdapter(danhMucChuongTrinhAdapter);
        floatThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new CSDLTruyenHinh(Activity_DanhMucChuongTrinh.this, "dbQLTH", null, 1);
                final Dialog dialog = new Dialog(Activity_DanhMucChuongTrinh.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_themdanhmuc);

                final EditText editThemMaCT = (EditText) dialog.findViewById(R.id.editThemMaCT);
                final EditText editThemTenCT = (EditText) dialog.findViewById(R.id.editThemTenCT);
                final EditText editThemMaTL = (EditText) dialog.findViewById(R.id.editThemMaTL);
                Button btnThemCT = (Button) dialog.findViewById(R.id.btnThemCT);
                Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnThemCT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maChuongTrinh = editThemMaCT.getText().toString() ;
                        String tenChuongTrinh = editThemTenCT.getText().toString();
                        String maTheLoai = editThemMaTL.getText().toString();
                        Boolean flagValid = true;
                        if (maChuongTrinh.isEmpty()) {
                            editThemMaCT.setError("Mã chương trình không được trống");
                            flagValid = false;
                        }
                        if (tenChuongTrinh.isEmpty()) {
                            editThemTenCT.setError("Tên chuong trình không được trống");
                            flagValid = false;
                        }
                        if (maTheLoai.isEmpty()) {
                            editThemMaTL.setError("Mã thể loại không được trống");
                            flagValid = false;
                        }
                        try {
                            if (flagValid){
                                db.themDanhMucCT(maChuongTrinh, tenChuongTrinh, maTheLoai);
                                //lvDanhMuc.setAdapter(danhMucChuongTrinhAdapter);
                                Toast.makeText(Activity_DanhMucChuongTrinh.this, " Thêm Chương Trình Thành Công", Toast.LENGTH_SHORT).show();
                                LoadData();
                                dialog.dismiss();

                            }
                        }catch (Exception e){
                            Toast.makeText(Activity_DanhMucChuongTrinh.this, "Mã Chương Trình Đã Tồn Tại", Toast.LENGTH_LONG).show();
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
        lvDanhMuc = (ListView) findViewById(R.id.lvDanhMuc);
        floatThem = (FloatingActionButton) findViewById(R.id.floatThem);
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