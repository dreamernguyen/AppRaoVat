package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.MatHangChiTietActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MatHangAdapter2 extends RecyclerView.Adapter<MatHangAdapter2.MatHangViewHolder> {
    private Context context;
    private List<MatHang> listMatHang;



    public MatHangAdapter2(Context context) {
        this.context = context;
    }

    public void setData(List<MatHang> list) {
        this.listMatHang = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MatHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mat_hang2, parent, false);
        return new MatHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatHangViewHolder holder, int position) {
        MatHang matHang = listMatHang.get(holder.getAdapterPosition());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("UTC+7"));

        try {
            Date date = format.parse(matHang.getThoiGianTao());
            long diff = now.getTime() - date.getTime();
            long giay = (diff / 1000);
            long phut = (diff / (1000 * 60));
            long gio = (diff / (1000 * 60 * 60));
            long ngay = (diff / (1000 * 60 * 60 * 24));

            if (ngay > 3) {
                holder.tvThoiGian.setText(format2.format(date));
            }
            if (ngay <= 3) {
                holder.tvThoiGian.setText(ngay + " ngày trước");
            }
            if (gio < 24 && gio > 0) {
                holder.tvThoiGian.setText(gio + " giờ trước");
            }
            if (phut < 60 && phut > 0) {
                holder.tvThoiGian.setText(phut + " phút trước");
            }
            if (giay < 60 && giay > 0) {
                holder.tvThoiGian.setText("Vừa xong");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(matHang.getLinkAnh().size() > 0){
            Glide.with(context).load(matHang.getLinkAnh().get(0)).into(holder.imageView);
        }
        holder.tvTieuDe.setText(matHang.getTieuDe());
        holder.tvGia.setText(matHang.getGiaBan()+ " VNĐ");
        holder.tvLuotQuanTam.setText("Có "+ matHang.getNguoiQuanTam().size()+" người quan tâm");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,MatHangChiTietActivity.class);
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
        if (listMatHang != null) {
            return listMatHang.size();
        }
        return 0;
    }

    public class MatHangViewHolder extends RecyclerView.ViewHolder {
        TextView tvTieuDe, tvThoiGian, tvGia,tvLuotQuanTam;
        ImageView imageView;
        LinearLayout layout;


        public MatHangViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgAnhMatHang);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            tvLuotQuanTam = itemView.findViewById(R.id.tvLuotQuanTam);
            tvGia = itemView.findViewById(R.id.tvGiaBan);
            layout = itemView.findViewById(R.id.layout);

        }
    }


}

