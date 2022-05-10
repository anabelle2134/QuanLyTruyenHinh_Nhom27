
package com.example.quanlitruyenhinh;

import android.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.quanlitruyenhinh.Adapter.BienTapVienAdapter;
import com.example.quanlitruyenhinh.Adapter.DanhMucChuongTrinhAdapter;
import com.example.quanlitruyenhinh.Adapter.ThongTinPhatSongAdapter;
import com.example.quanlitruyenhinh.MainActivity;
import com.example.quanlitruyenhinh.Model.DanhMucChuongTrinh;
import com.example.quanlitruyenhinh.Model.ThongTinPhatSong;
import com.example.quanlitruyenhinh.R;
import com.example.quanlitruyenhinh.CSDLTruyenHinh;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Activity_ThongTinPhatSong extends AppCompatActivity {
    ListView lvTTPS;
    ArrayList <ThongTinPhatSong> data = new ArrayList<>();
    ThongTinPhatSongAdapter thongTinPhatSongAdapter;
    FloatingActionButton floatThemTTPS;
    CSDLTruyenHinh db;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_phat_song);

        setControl();
        setEvent();
        LoadData();

    }
    public void LoadData(){
        CSDLTruyenHinh db = new CSDLTruyenHinh(this, "dbQLTH", null, 1);
        Cursor cursor = db.getData("SELECT * FROM THONGTINPHATSONG");
        data.clear();
        while (cursor.moveToNext()){
            String MaPS = cursor.getString(0);
            String MaBTV = cursor.getString(1);
            String maCT = cursor.getString(2);
            String ngayPS = cursor.getString(3);
            String thoiLuong = cursor.getString(4);
            data.add(new ThongTinPhatSong(MaPS,MaBTV, maCT, ngayPS, thoiLuong));

        }
        cursor.close();
        db.close();
        thongTinPhatSongAdapter.notifyDataSetChanged();
        lvTTPS.setAdapter(thongTinPhatSongAdapter);
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
                thongTinPhatSongAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                thongTinPhatSongAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void setEvent() {
        thongTinPhatSongAdapter = new ThongTinPhatSongAdapter(data, this, android.R.layout.simple_list_item_1);
        lvTTPS.setAdapter(thongTinPhatSongAdapter);
        floatThemTTPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new CSDLTruyenHinh(Activity_ThongTinPhatSong.this, "dbQLTH", null, 1);
                final Dialog dialog = new Dialog(Activity_ThongTinPhatSong.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_them_thongtinphatsong);

                final EditText editThemMaPS = (EditText) dialog.findViewById(R.id.editThemMaPS);
                final EditText editThemMaBTV = (EditText) dialog.findViewById(R.id.editThemMaBTV);
                final EditText editThemMaCT = (EditText) dialog.findViewById(R.id.editThemMaCT);
                final EditText editthoiLuong = (EditText) dialog.findViewById(R.id.editThemThoiLuong);
                final EditText editngayPS = (EditText) dialog.findViewById(R.id.editThemNgayPS);
                Button btnThemTTPS = (Button) dialog.findViewById(R.id.btnThemTTPS);
                Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnThemTTPS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maPhatSong = editThemMaPS.getText().toString() ;
                        String maChuongTrinh = editThemMaCT.getText().toString();
                        String maBTV = editThemMaBTV.getText().toString();
                        String ngayPS = editngayPS.getText().toString();
                        String thoiLuong = editthoiLuong.getText().toString();
                        Boolean flagValid = true;
                        if (maPhatSong.isEmpty()) {
                            editThemMaPS.setError("Mã phát sóng không được trống");
                            flagValid = false;
                        }
                        if (maChuongTrinh.isEmpty()) {
                            editThemMaCT.setError("Mã chương trình không được trống");
                            flagValid = false;
                        }
                        if (maBTV.isEmpty()) {
                            editThemMaBTV.setError("Mã BTV không được trống");
                            flagValid = false;
                        }
                        if (ngayPS.isEmpty()) {
                            editngayPS.setError("Ngày phát sóng không được trống");
                            flagValid = false;
                        }
                        if (thoiLuong.isEmpty()) {
                            editthoiLuong.setError("Thời lượng không được trống");
                            flagValid = false;
                        }

                        try {
                            if (flagValid){
                                db.themThongTinPhatSong(maPhatSong,  maChuongTrinh, maBTV, ngayPS, thoiLuong);
                                //lvDanhMuc.setAdapter(danhMucChuongTrinhAdapter);
                                Toast.makeText(Activity_ThongTinPhatSong.this, " Thêm ttps Thành Công", Toast.LENGTH_SHORT).show();
                                LoadData();
                                dialog.dismiss();

                            }
                        }catch (Exception e){
                            Toast.makeText(Activity_ThongTinPhatSong.this, "Mã PS Đã Tồn Tại", Toast.LENGTH_LONG).show();
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
        lvTTPS = (ListView) findViewById(R.id.lvTTPS);
        floatThemTTPS = (FloatingActionButton) findViewById(R.id.floatThemTTPS);
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
