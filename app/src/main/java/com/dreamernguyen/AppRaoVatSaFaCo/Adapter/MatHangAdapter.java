package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.MatHangChiTietActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatHangAdapter extends RecyclerView.Adapter<MatHangAdapter.MatHangViewHolder> {
    private Context context;
    private List<MatHang> listMatHang;

    public MatHangAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MatHang> list) {
        this.listMatHang = list;
        notifyDataSetChanged();
    }
    public void random(){
        Collections.shuffle(listMatHang);
    }

    @NonNull
    @Override
    public MatHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mat_hang, parent, false);
        return new MatHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatHangViewHolder holder, int position) {
        MatHang matHang = listMatHang.get(position);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        Log.d("now", "onBindViewHolder: " + now);
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
            if (ngay < 3) {
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

        holder.tvTieuDe.setText(matHang.getTieuDe());
        holder.tvGiaBan.setText( matHang.getGiaBan()+" VND");
        String thanhPho=matHang.getDiaChi().replaceAll("(?=\\-)(.*?)$", "");
        holder.tvDiaChi.setText(thanhPho);
        Glide.with(context).load(matHang.getLinkAnh().get(0)).into(holder.imgHinhAnh);
        holder.layoutChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= new Intent(context,MatHangChiTietActivity.class);
//                intent.putExtra("idMatHangChiTiet",matHang.getId());
                Intent i = new Intent(context,MatHangChiTietActivity.class);
                Bundle bundle = new Bundle();
// đóng gói kiểu dữ liệu String, Boolean
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



// đóng gói bundle vào intent
                i.putExtras(bundle);
// start SecondActivity

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
        TextView tvTieuDe, tvGiaBan, tvDiaChi, tvThoiGian, tvSoAnh;

        RelativeLayout layoutChiTiet;
        ImageView imgTuyChinh, imgHinhAnh;


        public MatHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);
            tvGiaBan = itemView.findViewById(R.id.tvGiaBan);
            tvDiaChi=itemView.findViewById(R.id.tvDiaChi);
            tvThoiGian=itemView.findViewById(R.id.tvThoiGian);
            imgHinhAnh=itemView.findViewById(R.id.imgHinhAnh);
            layoutChiTiet=itemView.findViewById(R.id.lnChiTiet);
        }
    }
}

