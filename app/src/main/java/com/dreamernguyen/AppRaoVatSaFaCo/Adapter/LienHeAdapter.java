package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.NhanTinActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TrangCaNhanActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.NguoiDung;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LienHeAdapter extends  RecyclerView.Adapter<LienHeAdapter.LienHeViewHolder> {
    private Context context;
    private List<NguoiDung> listLienHe;

    public LienHeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NguoiDung> list) {
        this.listLienHe = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LienHeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lien_he, parent, false);
        return new LienHeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LienHeViewHolder holder, int position) {
        NguoiDung nguoiDung = listLienHe.get(position);

        if (nguoiDung.getAvatar().length() > 0) {
            Glide.with(context).load(nguoiDung.getAvatar()).into(holder.imgAvatar);
        }
        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrangCaNhanActivity.class);
                intent.putExtra("idNguoiDung", nguoiDung.getId());
                context.startActivity(intent);
            }
        });
        holder.tvTenNguoiDung.setText(nguoiDung.getHoTen());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NhanTinActivity.class);
                intent.putExtra("activity", "MainActivity");
                intent.putExtra("idNguoiDung", nguoiDung.getId());
                intent.putExtra("tenNguoiDung", nguoiDung.getHoTen());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (listLienHe != null) {
            return listLienHe.size();
        }
        return 0;
    }

    public class LienHeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNguoiDung, tvNoiDung;
        CircleImageView imgAvatar;
        MaterialCardView cardView;


        public LienHeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            cardView = itemView.findViewById(R.id.cvLienHe);
        }
    }
}
