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
import com.example.quanlitruyenhinh.Model.DanhMucChuongTrinh;
import com.example.quanlitruyenhinh.R;
import com.example.quanlitruyenhinh.CSDLTruyenHinh;

import java.util.ArrayList;
import java.util.List;

public class DanhMucChuongTrinhAdapter extends BaseAdapter implements Filterable {
    private List <DanhMucChuongTrinh> danhMucChuongTrinhList;
    private List <DanhMucChuongTrinh> danhMucChuongTrinhListOld;
    Context context;
    int layout;
    CSDLTruyenHinh db;
    Filter filter;

    public DanhMucChuongTrinhAdapter(List<DanhMucChuongTrinh> danhMucChuongTrinhList, Context context, int layout) {
        this.danhMucChuongTrinhList = danhMucChuongTrinhList;
        this.context = context;
        this.layout = layout;
        this.danhMucChuongTrinhListOld = danhMucChuongTrinhList;
    }

    @Override
    public int getCount() {
        return danhMucChuongTrinhList.size();
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
                    danhMucChuongTrinhList = danhMucChuongTrinhListOld;
                }else {
                    List<DanhMucChuongTrinh> listCT = new ArrayList<>();
                    for(DanhMucChuongTrinh danhMucChuongTrinh : danhMucChuongTrinhListOld){
                        if(danhMucChuongTrinh.getTenChuongTrinh().toLowerCase().contains(strSearch.toLowerCase())){
                            listCT.add(danhMucChuongTrinh);
                        }
                    }
                    danhMucChuongTrinhList= listCT;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = danhMucChuongTrinhList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                danhMucChuongTrinhList = (List<DanhMucChuongTrinh>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    private class ViewHolder{
        TextView TenCT, MaCT, MaTheLoai;
        ImageView danhmucsua, danhmucxoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_danhmucchuongtrinh, null);
            holder.MaCT = (TextView) view.findViewById(R.id.MaCT);
            holder.TenCT = (TextView) view.findViewById(R.id.TenCT);
            holder.MaTheLoai = (TextView) view.findViewById(R.id.MaTheLoai);
            holder.danhmucxoa = (ImageView)view.findViewById(R.id.danhmucxoa);
            holder.danhmucsua = (ImageView) view.findViewById(R.id.danhmucsua);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final DanhMucChuongTrinh danhMucCT = danhMucChuongTrinhList.get(i);
        holder.MaCT.setText(danhMucCT.getMaChuongTrinh());
        holder.TenCT.setText(danhMucCT.getTenChuongTrinh());
        holder.MaTheLoai.setText(danhMucCT.getMaTheLoai());


        holder.danhmucsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_suadanhmuc);

                final EditText editSuaTenCT = (EditText) dialog.findViewById(R.id.editSuaTenCT);
                Button btnSuaTenCT = (Button) dialog.findViewById(R.id.btnSuaTenCT);
                Button btnHuySua = (Button) dialog.findViewById(R.id.btnHuyCT);
                editSuaTenCT.setText(danhMucCT.getTenChuongTrinh());
                btnHuySua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnSuaTenCT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String suaTen = editSuaTenCT.getText().toString();
                        String ma = danhMucCT.getMaChuongTrinh();
                        Boolean flagValid = true;
                        if (suaTen.isEmpty()) {
                            editSuaTenCT.setError("Mã chương trình không được trống");
                            flagValid = false;
                        }
                        try {
                            if (flagValid) {
                                CSDLTruyenHinh db = new CSDLTruyenHinh(context, "QuanLiTruyenHinh.db", null, 1);
                                db.suaDanhMucCT(suaTen, ma);
                                Toast.makeText(context, " Cập Nhật Chương Trình Thành Công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                ((Activity_DanhMucChuongTrinh) context).recreate();
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
                        String ma = danhMucCT.getMaChuongTrinh();
                        CSDLTruyenHinh db = new CSDLTruyenHinh(context, "QuanLiTruyenHinh.db", null, 1);
                        db.xoaDanhMucCT(ma);
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
