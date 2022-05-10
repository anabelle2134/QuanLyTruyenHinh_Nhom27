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

import com.example.quanlitruyenhinh.Activity_TheLoai;
import com.example.quanlitruyenhinh.Model.TheLoai;
import com.example.quanlitruyenhinh.R;
import com.example.quanlitruyenhinh.CSDLTruyenHinh;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiAdapter extends BaseAdapter implements Filterable {
    private List <TheLoai> theLoaiList;
    private List <TheLoai> theLoaioldList;
    Context context;
    int layout;
    CSDLTruyenHinh db;
    Filter filter;

    public TheLoaiAdapter(List<TheLoai> theLoaiList, Context context, int layout) {
        this.theLoaiList = theLoaiList;
        this.context = context;
        this.theLoaioldList = theLoaiList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return theLoaiList.size();
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
                    theLoaiList = theLoaioldList;
                }else {
                    List<TheLoai> listTL = new ArrayList<>();
                    for(TheLoai theLoai : theLoaioldList){
                        if(theLoai.getTenTL().toLowerCase().equals(strSearch.toLowerCase())){
                            listTL.add(theLoai);
                        }
                    }
                    theLoaiList=listTL;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=theLoaiList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                theLoaiList=(List<TheLoai>) results.values;
             //   bienTapVienList = (List<BienTapVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    private class ViewHolder{
        TextView TenTL, MaTL, moTa;
        ImageView danhmucsua, danhmucxoa;
    }
    //gan data len view

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_theloai, null);

            holder.MaTL = (TextView) view.findViewById(R.id.MaTL);
            holder.TenTL = (TextView) view.findViewById(R.id.TenTL);
            holder.moTa   = (TextView) view.findViewById(R.id.moTaTL) ;
            holder.danhmucxoa = (ImageView)view.findViewById(R.id.danhmucxoa);
            holder.danhmucsua = (ImageView) view.findViewById(R.id.danhmucsua);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final TheLoai danhmucTL = theLoaiList.get(i);
        holder.MaTL.setText(danhmucTL.getMaTL());
        holder.TenTL.setText(danhmucTL.getTenTL());
        holder.moTa.setText(danhmucTL.getMoTa());

        holder.danhmucsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_suatheloai);
                final EditText edtSuaTenTL= (EditText) dialog.findViewById(R.id.editSuaTenTL);
                 final  EditText edtSuaMotaTL = (EditText) dialog.findViewById(R.id.editSuamoTa);
                Button btnSuaTL = (Button) dialog.findViewById(R.id.btnSuaTL);
                Button btnHuySua = (Button) dialog.findViewById(R.id.btnHuyTL);
                edtSuaTenTL.setText(danhmucTL.getTenTL());
                edtSuaMotaTL.setText(danhmucTL.getMoTa());
                btnHuySua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnSuaTL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String suaTen= edtSuaTenTL.getText().toString();
                        String suaMoTa= edtSuaMotaTL.getText().toString();
                        String ma = danhmucTL.getMaTL();
                        Boolean flagValid = true;
                        if (suaTen.isEmpty()) {
                            edtSuaTenTL.setError("Tên không được trống");
                            flagValid = false;
                        }
                        if(suaMoTa.isEmpty()) {
                            edtSuaMotaTL.setError("KHÔNG ĐC ĐỂ TRỐNG");
                            //editSuaNamSinh.setError("Năm sinh không được trống");
                            flagValid = false;
                        }

                        try {
                            if (flagValid) {
                                CSDLTruyenHinh db = new CSDLTruyenHinh(context, "QuanLiTruyenHinh.db", null, 1);
                                db.suaTL(suaTen,suaMoTa,ma);
                                Toast.makeText(context, " Cập Nhật thể loại Thành Công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                ((Activity_TheLoai) context).recreate();
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
                        String ma = danhmucTL.getMaTL();
                       // String ma = danhMucBTV.getMaBTV();
                        CSDLTruyenHinh db = new CSDLTruyenHinh(context, "QuanLiTruyenHinh.db", null, 1);
                        db.xoaTL(ma);
                        Toast.makeText(context, " Xóa Thành Công ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        ((Activity_TheLoai)context).recreate();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }


}
