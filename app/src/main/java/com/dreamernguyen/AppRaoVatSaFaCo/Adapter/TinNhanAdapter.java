package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.NhanTinActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TinNhan;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TinNhanAdapter extends  RecyclerView.Adapter<TinNhanAdapter.TinNhanViewHolder> {
    private Context context;
    private List<TinNhan> listTinNhan;

    public TinNhanAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TinNhan> list) {
        this.listTinNhan = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TinNhanAdapter.TinNhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tin_nhan, parent, false);
        return new TinNhanAdapter.TinNhanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TinNhanAdapter.TinNhanViewHolder holder, int position) {
        TinNhan tinNhan = listTinNhan.get(position);
        if (tinNhan == null){
            return;
        }
        if(tinNhan.getIdNguoiNhan().getId().equals(LocalDataManager.getIdNguoiDung())){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.START;
            holder.layoutChat.setLayoutParams(params);
            holder.layoutChat.setBackground(context.getDrawable(R.drawable.bg_chat2));
        }else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;
            holder.layoutChat.setLayoutParams(params);
            holder.layoutChat.setBackground(context.getDrawable(R.drawable.bg_chat));
        }
        holder.tvChat.setText(tinNhan.getNoiDung());
        holder.imageView.setVisibility(View.GONE);
        if(tinNhan.getLinkAnh().trim().length() == 0){
            holder.tvChat.setText(tinNhan.getNoiDung());
            holder.tvChat.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }else {
            holder.tvChat.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(tinNhan.getLinkAnh()).into(holder.imageView);
        }
        holder.tvXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<DuLieuTraVe> call = ApiService.apiService.xoaTinNhan(tinNhan.getId(),LocalDataManager.getIdNguoiDung());
                call.enqueue(new Callback<DuLieuTraVe>() {
                    @Override
                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                        listTinNhan.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        Toast.makeText(context, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                        Toast.makeText(context, "Lỗi xóa tin"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        if (listTinNhan != null) {
            return listTinNhan.size();
        }
        return 0;
    }

    public class TinNhanViewHolder extends RecyclerView.ViewHolder {
        TextView tvChat,tvXoa;
        LinearLayout layoutChat;
        ImageView imageView;


        public TinNhanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChat = (TextView) itemView.findViewById(R.id.tvChat);
            tvXoa = (TextView) itemView.findViewById(R.id.tvXoa);
            layoutChat = (LinearLayout) itemView.findViewById(R.id.layoutChat);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
