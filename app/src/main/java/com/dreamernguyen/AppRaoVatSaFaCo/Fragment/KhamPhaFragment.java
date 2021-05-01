package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.BaiVietAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangBaiActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.BaiViet;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KhamPhaFragment extends Fragment {
    RecyclerView rvBaiViet;
    BaiVietAdapter baiVietAdapter;
    SwipeRefreshLayout refreshLayout;
    TextView tv;
    CircleImageView imAvatar;
    ScrollView layoutLoading;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kham_pha, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        layoutLoading = view.findViewById(R.id.listLoading);
        tv = view.findViewById(R.id.tv1);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),DangBaiActivity.class);
                i.putExtra("chucNang","Đăng bài");
                startActivity(i);
            }
        });
        imAvatar = view.findViewById(R.id.avatar);
        Glide.with(getContext()).load(LocalDataManager.getNguoiDung().getAvatar()).into(imAvatar);
        rvBaiViet =  view.findViewById(R.id.rvBaiViet);
        baiVietAdapter = new BaiVietAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvBaiViet.setLayoutManager(linearLayoutManager);
        rvBaiViet.setAdapter(baiVietAdapter);
        loadBaiVietKhamPha();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBaiVietKhamPha();

            }
        });
//        Glide.with(this).load(LocalDataManager.getNguoiDung().getAvatar()).circleCrop().into(imAvatar);
        return view;

    }
    public void loadBaiVietKhamPha(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachBaiViet();
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                List<BaiViet> listBaiViet = response.body().getDanhSachBaiViet();
                layoutLoading.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                if(listBaiViet.size() > 0){
                    List<BaiViet> listHienThi = new ArrayList<>();
                    for(int i= 0; i < listBaiViet.size() ;i++){
                        if(!listBaiViet.get(i).getAnBaiVoi().contains(LocalDataManager.getIdNguoiDung())){
                            listHienThi.add(listBaiViet.get(i));
                        }
                    }
                    baiVietAdapter.setData(listHienThi);
                    baiVietAdapter.random();
                }else {
                    Log.d("loadBaiVietKhamPha", "onFailure: danh sách bài viết trống");
                }
            }
            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadBaiVietKhamPha", "onFailure: "+t.getMessage());
                Toast.makeText(getActivity(), "Lỗi load bài viết khám phá !\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}