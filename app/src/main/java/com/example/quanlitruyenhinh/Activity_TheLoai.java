package com.example.quanlitruyenhinh;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.quanlitruyenhinh.Adapter.TheLoaiAdapter;
import com.example.quanlitruyenhinh.Model.TheLoai;
import com.example.quanlitruyenhinh.CSDLTruyenHinh;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Activity_TheLoai extends AppCompatActivity {
    ListView lvTL;
    ArrayList<TheLoai> data = new ArrayList<>();
    TheLoaiAdapter theLoaiAdapter;
    // BienTapVienAdapter bienTapVienAdapter;
    FloatingActionButton floatThem;
    CSDLTruyenHinh db;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theloai);

        setControl();
        setEvent();
        LoadData();

    }
    public void LoadData(){
        CSDLTruyenHinh db = new CSDLTruyenHinh(this, "dbQLTH", null, 1);
        Cursor cursor = db.getData("SELECT * FROM THELOAI");
        data.clear();
        while (cursor.moveToNext()){
            String tenTL = cursor.getString(1);
            String maTL = cursor.getString(0);
            String moTa = cursor.getString(2);
            TheLoai tl= new TheLoai(maTL,tenTL,moTa);
            data.add(tl);

        }

        theLoaiAdapter.notifyDataSetChanged();
        lvTL.setAdapter(theLoaiAdapter);
        //   bienTapVienAdapter.notifyDataSetChanged();
        // lvBTV.setAdapter(bienTapVienAdapter);
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
                theLoaiAdapter.getFilter().filter(query);
                //bienTapVienAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                theLoaiAdapter.getFilter().filter(newText);
                //bienTapVienAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void setEvent() {
        theLoaiAdapter = new TheLoaiAdapter(data,this, android.R.layout.simple_list_item_1);
        //   bienTapVienAdapter = new BienTapVienAdapter(data, this, android.R.layout.simple_list_item_1);
        lvTL.setAdapter(theLoaiAdapter);
        floatThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new CSDLTruyenHinh(Activity_TheLoai.this, "dbQLTH", null, 1);
                final Dialog dialog = new Dialog(Activity_TheLoai.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_thembientheloai);

                final EditText editThemMaBTV = (EditText) dialog.findViewById(R.id.editThemMaTL);
                final EditText editThemTenBTV = (EditText) dialog.findViewById(R.id.editThemTenTL);
                final EditText editThemSDT = (EditText) dialog.findViewById(R.id.editThemmoTa);
                Button btnThemBTV = (Button) dialog.findViewById(R.id.btnThemTL);
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
                        String maTL= editThemMaBTV.getText().toString();
                        String tenTL= editThemTenBTV.getText().toString();
                        String moTa= editThemSDT.getText().toString();
                        Boolean flagValid = true;
                        if (maTL.isEmpty()) {
                            editThemMaBTV.setError("Mã thể loại không được trống");
                            flagValid = false;
                        }
                        if (tenTL.isEmpty()) {
                            editThemTenBTV.setError("Tên thể loại không được trống");
                            flagValid = false;
                        }

                        if (moTa.isEmpty()) {
                            editThemSDT.setError("mô tả không được trống");
                            flagValid = false;
                        }
                        try {
                            if (flagValid){
                                db.themTL(maTL, tenTL, moTa);
                                //lvDanhMuc.setAdapter(danhMucChuongTrinhAdapter);
                                Toast.makeText(Activity_TheLoai.this, " Thêm BTV Thành Công", Toast.LENGTH_SHORT).show();
                                LoadData();
                                dialog.dismiss();

                            }
                        }catch (Exception e){
                            Toast.makeText(Activity_TheLoai.this, "Mã BTV Đã Tồn Tại", Toast.LENGTH_LONG).show();
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
        lvTL =(ListView) findViewById(R.id.lvTL);
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
