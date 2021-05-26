package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TimKiemMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class TimKiemNhanhLichSuAdapter extends RecyclerView.Adapter<TimKiemNhanhLichSuAdapter.LichSuViewHolder> {

    Context context;
    List<TimKiem> listLichSu;

    public TimKiemNhanhLichSuAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TimKiem> listLichSu){
        this.listLichSu = listLichSu;

    }

    @NonNull
    @Override
    public LichSuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lich_su,parent ,false);

        return new LichSuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuViewHolder holder, int position) {

        TimKiem timKiem= listLichSu.get(holder.getAdapterPosition());
        holder.tvTenLichSu.setText(timKiem.getTieuDe());

        holder.tvTenLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_timKiemMatHangNhanhFragment_to_timKiemMatHangKetQuaFragment);

                TimKiem timKiem1 = TimKiemMatHangActivity.timKiemTemp;
                timKiem1.setTieuDe(timKiem.getTieuDe());
                TimKiemDataBase.getInstance(context).timKiemDAO().UpdateTemp(timKiem);
            }
        });
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimKiemDataBase.getInstance(context).timKiemDAO().delete(timKiem);

                listLichSu.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listLichSu!=null)
        {
            if (listLichSu.size()>3){
                return 4;
            }
            return listLichSu.size();
        }
        return 0;
    }

    public class LichSuViewHolder extends RecyclerView.ViewHolder {

        MaterialButton btn ;
        TextView tvTenLichSu;
        public LichSuViewHolder(@NonNull View itemView) {
            super(itemView);

            btn=itemView.findViewById(R.id.btn);
            tvTenLichSu=itemView.findViewById(R.id.tvTenLichSu);
        }
    }
}
