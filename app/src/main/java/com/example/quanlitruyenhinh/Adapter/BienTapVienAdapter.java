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

import com.example.quanlitruyenhinh.Activity_BienTapVien;
import com.example.quanlitruyenhinh.Model.BienTapVien;
import com.example.quanlitruyenhinh.R;
import com.example.quanlitruyenhinh.CSDLTruyenHinh;

import java.util.ArrayList;
import java.util.List;

public class BienTapVienAdapter extends BaseAdapter implements Filterable {
    private List <BienTapVien> bienTapVienList;
    private List <BienTapVien> bienTapVienListOld;
    Context context;
    int layout;
    CSDLTruyenHinh db;
    Filter filter;

    public BienTapVienAdapter(List<BienTapVien> bienTapVienList, Context context, int layout) {
        this.bienTapVienList = bienTapVienList;
        this.context = context;
        this.layout = layout;
        this.bienTapVienListOld = bienTapVienList;
    }

    @Override
    public int getCount() {
        return bienTapVienList.size();
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
                if(strSearch.isEmpty()){
                    bienTapVienList = bienTapVienListOld;
                }else {
                    List<BienTapVien> listBTV = new ArrayList<>();
                    for(BienTapVien bienTapVien : bienTapVienListOld){
                        if(bienTapVien.getTenBTV().toLowerCase().contains(strSearch.toLowerCase())){
                            listBTV.add(bienTapVien);
                        }
                    }
                    bienTapVienList= listBTV;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = bienTapVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                bienTapVienList = (List<BienTapVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    private class ViewHolder{
        TextView TenBTV, MaBTV, NamSinh, SDT;
        ImageView danhmucsua, danhmucxoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_bientapvien, null);
            holder.MaBTV = (TextView) view.findViewById(R.id.MaBTV);
            holder.TenBTV = (TextView) view.findViewById(R.id.TenBTV);
            holder.NamSinh = (TextView) view.findViewById(R.id.NamSinh);
            holder.SDT = (TextView) view.findViewById(R.id.SDT);
            holder.danhmucxoa = (ImageView)view.findViewById(R.id.danhmucxoa);
            holder.danhmucsua = (ImageView) view.findViewById(R.id.danhmucsua);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final BienTapVien danhMucBTV = bienTapVienList.get(i);
        holder.MaBTV.setText(danhMucBTV.getMaBTV());
        holder.TenBTV.setText(danhMucBTV.getTenBTV());
        holder.NamSinh.setText(danhMucBTV.getNamSinh());
        holder.SDT.setText(danhMucBTV.getsDT());


        holder.danhmucsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_suabientapvien);

                final EditText editSuaTenBTV = (EditText) dialog.findViewById(R.id.editSuaTenBTV);
                final EditText editSuaNamSinh = (EditText) dialog.findViewById(R.id.editSuaNS);
                final EditText editSuaSDT = (EditText) dialog.findViewById(R.id.editSuaSDT);
                Button btnSuaTenBTV = (Button) dialog.findViewById(R.id.btnSuaBTV);
                Button btnHuySua = (Button) dialog.findViewById(R.id.btnHuyBTV);
                editSuaTenBTV.setText(danhMucBTV.getTenBTV());
                editSuaNamSinh.setText(danhMucBTV.getNamSinh());
                editSuaSDT.setText(danhMucBTV.getsDT());
                btnHuySua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnSuaTenBTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String suaTen = editSuaTenBTV.getText().toString();
                        String suaNS = editSuaNamSinh.getText().toString();
                        String suaSDT = (editSuaSDT.getText().toString());
                        String ma = danhMucBTV.getMaBTV();
                        Boolean flagValid = true;
                        if (suaTen.isEmpty()) {
                            editSuaTenBTV.setError("Mã BTV không được trống");
                            flagValid = false;
                        }
                        if(suaNS.isEmpty()) {
                            editSuaNamSinh.setError("Năm sinh không được trống");
                            flagValid = false;
                        }
                        if(suaSDT.isEmpty()){
                            editSuaSDT.setError("SĐT không được trống");
                            flagValid = false;
                        }
                        try {
                            if (flagValid) {
                                CSDLTruyenHinh db = new CSDLTruyenHinh(context, "QuanLiTruyenHinh.db", null, 1);
                               // db.suaBTV(suaTen, suaNS, suaSDT, ma);
                                db.suaBTV(suaTen, suaNS, suaSDT, ma);
                                Toast.makeText(context, " Cập Nhật Chương Trình Thành Công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                ((Activity_BienTapVien) context).recreate();
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
                        String ma = danhMucBTV.getMaBTV();
                        CSDLTruyenHinh db = new CSDLTruyenHinh(context, "QuanLiTruyenHinh.db", null, 1);
                        db.xoaBTV(ma);
                        Toast.makeText(context, " Xóa Thành Công ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        ((Activity_BienTapVien)context).recreate();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }


}
