package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.BaiVietChiTietActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.MatHangChiTietActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TrangCaNhanActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.ThongBao;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ThongBaoAdapter extends  RecyclerView.Adapter<ThongBaoAdapter.ThongBaoViewHolder> {
    private Context context;
    private List<ThongBao> listThongBao;

    public ThongBaoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ThongBao> list) {
        this.listThongBao = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thong_bao, parent, false);
        return new ThongBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ThongBao thongBao = listThongBao.get(position);
        if(thongBao != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat format2 = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
            format.setTimeZone(TimeZone.getTimeZone("UTC+7"));

            try {
                Date date = format.parse(thongBao.getThoiGianTao());
                holder.tvThoiGian.setText(format2.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            holder.tvNoiDung.setText(thongBao.getNoiDung());

            if(thongBao.getLoaiThongBao().equals("BaiViet")){
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BaiVietChiTietActivity.class);
                        intent.putExtra("chucNang","Xem");
                        intent.putExtra("idBaiViet", thongBao.getIdTruyXuat());
                        context.startActivity(intent);
                    }
                });
            }
            if(thongBao.getLoaiThongBao().equals("NguoiDung")){
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TrangCaNhanActivity.class);
                        intent.putExtra("chucNang","Xem");
                        intent.putExtra("idNguoiDung", thongBao.getIdTruyXuat());
                        context.startActivity(intent);
                    }
                });
            }
            if(thongBao.getLoaiThongBao().equals("MatHang")){
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,MatHangChiTietActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("activity","ThongBao");
                        bundle.putString("idMatHang",thongBao.getIdTruyXuat() );
                        i.putExtras(bundle);
                        context.startActivity(i);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        if (listThongBao != null) {
            return listThongBao.size();
        }
        return 0;
    }

    public class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoiDung,tvThoiGian;
        LinearLayout layout;

        public ThongBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
