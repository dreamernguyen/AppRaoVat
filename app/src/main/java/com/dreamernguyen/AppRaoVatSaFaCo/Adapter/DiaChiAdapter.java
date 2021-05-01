package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Quan;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Tinh;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Xa;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.List;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.DiaChiViewHolder> {
    private Context context;
    private List<Tinh> listTinh;
    private List<Quan> listQuan;
    private List<Xa> listXa;
    onClickThongTin onClickThongTin;

    public DiaChiAdapter(Context context) {
        this.context = context;
    }

    public interface onClickThongTin {

        void getString(String ma, String Ten);

    }

    public DiaChiAdapter(Context context, DiaChiAdapter.onClickThongTin onClickThongTin) {
        this.context = context;
        this.onClickThongTin = onClickThongTin;
    }

    public void setListTinh(List<Tinh> list) {
        this.listTinh = list;
        notifyDataSetChanged();
    }

    public void setListQuan(List<Quan> list) {
        this.listQuan = list;
        notifyDataSetChanged();
    }

    public void setListXa(List<Xa> list) {
        this.listXa = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DiaChiAdapter.DiaChiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dia_chi, parent, false);
        return new DiaChiAdapter.DiaChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiAdapter.DiaChiViewHolder holder, int position) {
        if (listTinh != null && !listTinh.isEmpty()) {
            Tinh tinh = listTinh.get(position);
            holder.tvTenDiaChi.setText(tinh.getTenTinh());
            holder.tvTenDiaChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickThongTin.getString(tinh.getMaTinh(), tinh.getTenTinh());
                }
            });


        }
        if (listQuan != null && !listQuan.isEmpty()) {
            Quan quan = listQuan.get(position);
            holder.tvTenDiaChi.setText(quan.getTenQuan());
            holder.tvTenDiaChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickThongTin.getString(quan.getMaQuan(), quan.getTenQuan());

                }
            });

        }
        if (listXa != null && !listXa.isEmpty()) {
            Xa xa = listXa.get(position);
            holder.tvTenDiaChi.setText(xa.getTenXa());
            holder.tvTenDiaChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickThongTin.getString(xa.getMaXa(), xa.getTenXa());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (listTinh != null) {
            return listTinh.size();
        }
        if (listQuan != null) {
            return listQuan.size();
        }
        if (listXa != null) {
            return listXa.size();
        }
        return 0;
    }

    public class DiaChiViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenDiaChi;

        public DiaChiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDiaChi = itemView.findViewById(R.id.tvTenDiaChi);
        }
    }
}
