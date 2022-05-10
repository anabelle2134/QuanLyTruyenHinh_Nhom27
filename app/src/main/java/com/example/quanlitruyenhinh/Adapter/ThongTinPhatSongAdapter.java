package com.example.quanlitruyenhinh.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlitruyenhinh.Activity_DanhMucChuongTrinh;
import com.example.quanlitruyenhinh.Activity_ThongTinPhatSong;
import com.example.quanlitruyenhinh.Model.ThongTinPhatSong;
import com.example.quanlitruyenhinh.R;
import com.example.quanlitruyenhinh.CSDLTruyenHinh;

import java.util.ArrayList;
import java.util.List;

public class ThongTinPhatSongAdapter extends BaseAdapter implements Filterable {
    private List<ThongTinPhatSong> thongTinPhatSongList;
    private List<ThongTinPhatSong> thongTinPhatSongListOld;
    Context context;
    int layout;
    CSDLTruyenHinh db;
    Filter filter;

    public ThongTinPhatSongAdapter(List<ThongTinPhatSong> thongTinPhatSongList, Context context, int layout) {
        this.thongTinPhatSongList = thongTinPhatSongList;
        this.context = context;
        this.layout = layout;
        this.thongTinPhatSongListOld = thongTinPhatSongList;
    }

    @Override
    public int getCount() {
        return thongTinPhatSongList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    thongTinPhatSongList = thongTinPhatSongListOld;
                } else {
                    List<ThongTinPhatSong> listTTPS = new ArrayList<>();
                    for (ThongTinPhatSong thongTinPhatSong : thongTinPhatSongListOld) {
                        if (thongTinPhatSong.getMaPhatSong().toLowerCase().contains(strSearch.toLowerCase())) {
                            listTTPS.add(thongTinPhatSong);
                        }
                    }
                    thongTinPhatSongList = listTTPS;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = thongTinPhatSongList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                thongTinPhatSongList = (List<ThongTinPhatSong>) results.values;
                notifyDataSetChanged();
            }
        };
    }

        private class ViewHolder {
            TextView MaPhatSong, MaBTV, MaChuongTrinh, ngayPhatSong, thoiLuong;
            ImageView danhmucsua, danhmucxoa;
        }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_thongtinphatsong, null);
            holder.MaPhatSong = (TextView) view.findViewById(R.id.MaPS);
            holder.MaBTV = (TextView) view.findViewById(R.id.MaBTV);
            holder.MaChuongTrinh = (TextView) view.findViewById(R.id.MaCT);
            holder.ngayPhatSong = (TextView) view.findViewById(R.id.NgayPS);
            holder.thoiLuong = (TextView) view.findViewById(R.id.Thoiluong);
            holder.danhmucxoa = (ImageView)view.findViewById(R.id.danhmucxoa);
            holder.danhmucsua = (ImageView) view.findViewById(R.id.danhmucsua);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final ThongTinPhatSong TTPS = thongTinPhatSongList.get(i);
        holder.MaPhatSong.setText(TTPS.getMaPhatSong());
        holder.MaChuongTrinh.setText(TTPS.getMaChuongTrinh());
        holder.MaBTV.setText(TTPS.getMaBTV());
        holder.ngayPhatSong.setText(TTPS.getNgayPhatSong());
        holder.thoiLuong.setText(TTPS.getThoiLuong());


        holder.danhmucsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_sua_thongtinphatsong);

                //final EditText editTTPS = (EditText) dialog.findViewById(R.id.btnSuaTTPS);
                final EditText editThoiLuong = (EditText) dialog.findViewById(R.id.editSuaThoiLuong);
                final EditText editNgayPS = (EditText) dialog.findViewById(R.id.editSuaNgayPS);
                final EditText editMaBTV = (EditText) dialog.findViewById(R.id.editSuaMaBTV);
                final EditText editMaCT = (EditText) dialog.findViewById(R.id.editSuaMaCT);
                Button btnSuaTTPS = (Button) dialog.findViewById(R.id.btnSuaTTPS);
                Button btnHuySua = (Button) dialog.findViewById(R.id.btnHuyCT);
                editThoiLuong.setText(TTPS.getThoiLuong());
                editNgayPS.setText(TTPS.getNgayPhatSong());
                btnHuySua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnSuaTTPS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String suaThoiLuong = editThoiLuong.getText().toString();
                        String suaNgayPS = editNgayPS.getText().toString();
                        String suamaBTV = editMaBTV.getText().toString();
                        String suamaCT = editMaCT.getText().toString();
                        String ma = TTPS.getMaPhatSong();
                        Boolean flagValid = true;
                        if (suaThoiLuong.isEmpty()) {
                            editThoiLuong.setError("Thời lượng không được trống");
                            flagValid = false;
                        }
                        if (suaNgayPS.isEmpty()) {
                            editNgayPS.setError("Ngày phát sóng không được trống");
                            flagValid = false;
                        }
                        if (suamaCT.isEmpty()) {
                            editMaCT.setError("Mã CT không được trống");
                            flagValid = false;
                        }
                        if (suamaBTV.isEmpty()) {
                            editMaBTV.setError("BTV không được trống");
                            flagValid = false;
                        }
                        try {
                            if (flagValid) {
                                CSDLTruyenHinh db = new CSDLTruyenHinh(context, "QuanLiTruyenHinh.db", null, 1);
                                db.suaThongTinPhatSong(suamaBTV, suamaCT, suaThoiLuong, suaNgayPS, ma);
                                Toast.makeText(context, " Cập Nhật TTPS Thành Công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                ((Activity_ThongTinPhatSong) context).recreate();
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, "Cập Nhật Không Thành Công",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        });

        holder.danhmucxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_xoadanhmuc);

                Button btnxoaTenCT = (Button) dialog.findViewById(R.id.btn_chapnhanxoa);
                Button btnhuyxoa = (Button) dialog.findViewById(R.id.btn_huyxoa);
                btnhuyxoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnxoaTenCT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ma = TTPS.getMaPhatSong();
                        CSDLTruyenHinh db = new CSDLTruyenHinh(context, "QuanLiTruyenHinh.db", null, 1);
                        db.xoaThongTinPhatSong(TTPS);
                        Toast.makeText(context, " Xóa Thành Công ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        ((Activity_DanhMucChuongTrinh)context).recreate();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }

}
