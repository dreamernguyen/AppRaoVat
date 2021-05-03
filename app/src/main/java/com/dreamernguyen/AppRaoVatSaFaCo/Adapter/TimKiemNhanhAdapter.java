package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.MatHangChiTietActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimKiemNhanhAdapter extends RecyclerView.Adapter<TimKiemNhanhAdapter.TimKiemNhanhViewHolder> implements Filterable {

    Context context;
    List<MatHang> list;
    List<MatHang> listCu;


    public void setData(List<MatHang> list) {
        this.list = list;
        this.listCu = list;
    }

    public TimKiemNhanhAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TimKiemNhanhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_tim_kiem_nhanh, parent, false);
        return new TimKiemNhanhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimKiemNhanhViewHolder holder, int position) {

        MatHang matHang = list.get(holder.getAdapterPosition());

        if (matHang == null) {
            return;

        }
        Glide.with(context).load(matHang.getLinkAnh().get(0)).into(holder.img);
        holder.tvTenMatHang.setText(matHang.getTieuDe());
        holder.tvDanhMuc.setText(matHang.getHangMuc());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MatHangChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("activity","Adapter");
                bundle.putString("idMatHang",matHang.getId() );
                bundle.putString("hoTen",matHang.getIdNguoiDung().getHoTen() );
                bundle.putString("hinhAnh",matHang.getLinkAnh().get(0));
                bundle.putString("moTa",matHang.getMoTa() );
                bundle.putString("tieuDe",matHang.getTieuDe());
                bundle.putString("diaChi",matHang.getDiaChi());
                bundle.putInt("giaBan", matHang.getGiaBan());
                bundle.putString("soDienThoai",matHang.getIdNguoiDung().getSoDienThoai());
                bundle.putString("thoiGian",matHang.getThoiGianTao());
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String a = constraint.toString();
                Log.d("TAG", "performFiltering: "+a);
                if (a.isEmpty()){
                    list=null;
                }
                else {
                    List<MatHang> list1 = new ArrayList<>();

                    for (MatHang matHang : listCu){
                        if (matHang.getTieuDe().toLowerCase().contains(a.toLowerCase())){
                            list1.add(matHang);

                        }
                    }
                    list= list1;
                    Log.d("TAG", "performFiltering: list "+list1);
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values=list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list = (List<MatHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class TimKiemNhanhViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView tvTenMatHang,tvDanhMuc;

        LinearLayout layout;
        public TimKiemNhanhViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.layout);
            img = itemView.findViewById(R.id.img);
            tvTenMatHang = itemView.findViewById(R.id.tvTenMatHang);
            tvDanhMuc = itemView.findViewById(R.id.tvDanhMuc);
        }
    }
}
