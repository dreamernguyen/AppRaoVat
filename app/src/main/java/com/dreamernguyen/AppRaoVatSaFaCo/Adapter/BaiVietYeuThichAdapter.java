package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.BaiVietChiTietActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangBaiActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TrangCaNhanActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.BaiViet;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiVietYeuThichAdapter extends RecyclerView.Adapter<BaiVietYeuThichAdapter.BaiVietViewHolder> {
    private AnhBaiVietAdapter anhBaiVietAdapter;
    private Context context;
    private List<BaiViet> listBaiViet;



    public BaiVietYeuThichAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BaiViet> list) {
        this.listBaiViet = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BaiVietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bai_viet2, parent, false);
        return new BaiVietViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaiVietViewHolder holder, int position) {
        BaiViet baiViet = listBaiViet.get(holder.getAdapterPosition());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("UTC+7"));

        try {
            Date date = format.parse(baiViet.getThoiGianTao());
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

        if(baiViet.getLinkAnh().size() > 0){
            anhBaiVietAdapter  = new AnhBaiVietAdapter(baiViet.getLinkAnh(),false);
            Glide.with(context).load(baiViet.getLinkAnh().get(0)).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        if (listBaiViet != null) {
            return listBaiViet.size();
        }
        return 0;
    }

    public class BaiVietViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNguoiDung, tvThoiGian, tvNoiDung;
        ImageView imageView;


        public BaiVietViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgAnhBaiViet);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian2);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);

        }
    }


}

