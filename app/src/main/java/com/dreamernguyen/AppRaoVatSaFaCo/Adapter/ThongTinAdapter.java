package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.ArrayList;
import java.util.List;

public class ThongTinAdapter extends RecyclerView.Adapter<ThongTinAdapter.ThongTinViewHolder>  {
    Context context;
    List<String> mlist;
    List<String> mlistFull;


    clickitimTimKiem clickitimTimKiem;

    public void setData(List<String> mlist) {
        this.mlist = mlist;
        mlistFull = new ArrayList<>();
    }

    public ThongTinAdapter(Context context, ThongTinAdapter.clickitimTimKiem clickitimTimKiem) {
        this.context = context;
        this.clickitimTimKiem = clickitimTimKiem;

    }

    public interface clickitimTimKiem {

        void getString(String a);

    }

    @NonNull
    @Override
    public ThongTinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_thong_tin, parent, false);
        return new ThongTinViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongTinViewHolder holder, int position) {

        final String a = mlist.get(position);
        switch (a) {
            case "Bất Động Sản":
                holder.img.setImageResource(R.drawable.bat_dong_san);
                break;
            case "Xe Cộ":
                holder.img.setImageResource(R.drawable.xe_co);
                break;
            case "Đồ Điện Tử":
                holder.img.setImageResource(R.drawable.do_dien_tu);
                break;
            case "Sách":
                holder.img.setImageResource(R.drawable.sach);
                break;
            case "Thời Trang":
                holder.img.setImageResource(R.drawable.thoi_trang);

                break;
            default:
                holder.img.setImageResource(R.drawable.anhdemo1);

        }
        holder.tv.setText(a);
        Log.d("TAG", "onBindViewHolder: " + a);
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickitimTimKiem.getString(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    public class ThongTinViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView img;

        public ThongTinViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            img = itemView.findViewById(R.id.img);
        }
    }
}
