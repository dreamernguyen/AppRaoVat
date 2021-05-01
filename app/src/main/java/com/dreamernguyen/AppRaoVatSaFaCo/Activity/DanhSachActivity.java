package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.BaiVietAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.BaiVietYeuThichAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.MatHangAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.MatHangAdapter2;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachActivity extends AppCompatActivity {
    RecyclerView rvDanhSach;
    BaiVietYeuThichAdapter baiVietYeuThichAdapter;
    MatHangAdapter2 matHangAdapter;
    SwipeRefreshLayout refreshLayout;
    TextView tvThongBao,tvTenDanhSach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        refreshLayout = findViewById(R.id.refreshLayout);
        rvDanhSach =  findViewById(R.id.rvDanhSach);
        tvThongBao = findViewById(R.id.tvThongBao);
        tvTenDanhSach = findViewById(R.id.tvTenDanhSach);

        Intent i = getIntent();
        if(i.getExtras() != null){
            if(i.getStringExtra("ChucNang").equals("BaiVietYeuThich")){
                tvTenDanhSach.setText("Danh sách bài viết yêu thích");
                baiVietYeuThichAdapter = new BaiVietYeuThichAdapter(DanhSachActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachActivity.this, RecyclerView.VERTICAL,false);
                rvDanhSach.setLayoutManager(linearLayoutManager);
                rvDanhSach.setAdapter(baiVietYeuThichAdapter);
                loadBaiVietYeuThich();
                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadBaiVietYeuThich();
                    }
                });
            }
            if(i.getStringExtra("ChucNang").equals("BaiVietDaAn")){
                tvTenDanhSach.setText("Danh sách bài viết đã ẩn");
                baiVietYeuThichAdapter = new BaiVietYeuThichAdapter(DanhSachActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachActivity.this, RecyclerView.VERTICAL,false);
                rvDanhSach.setLayoutManager(linearLayoutManager);
                rvDanhSach.setAdapter(baiVietYeuThichAdapter);
                loadBaiVietDaAn();
                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadBaiVietDaAn();
                    }
                });
            }
            if(i.getStringExtra("ChucNang").equals("MatHangQuanTam")){
                tvTenDanhSach.setText("Danh sách mặt hàng quan tâm");
                matHangAdapter = new MatHangAdapter2(DanhSachActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachActivity.this, RecyclerView.VERTICAL,false);
                rvDanhSach.setLayoutManager(linearLayoutManager);
                rvDanhSach.setAdapter(matHangAdapter);
                loadMatHangQuanTam();
                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadMatHangQuanTam();
                    }
                });
            }
        }


    }

    private void loadBaiVietDaAn() {
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachBaiVietAn(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                refreshLayout.setRefreshing(false);
                if(response.body().getDanhSachBaiViet().size() >0 ){
                    tvThongBao.setVisibility(View.GONE);
                    rvDanhSach.setVisibility(View.VISIBLE);
                    baiVietYeuThichAdapter.setData(response.body().getDanhSachBaiViet());
                }else {
                    tvThongBao.setVisibility(View.VISIBLE);
                    tvThongBao.setText(response.body().getThongBao());
                    rvDanhSach.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadBaiVietDaAn", "onFailure: "+t.getMessage());
            }
        });
    }

    public void loadBaiVietYeuThich(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachYeuThich(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                refreshLayout.setRefreshing(false);
                Log.d("Data", "onResponse: "+response.body().getDanhSachBaiViet());
                if(response.body().getDanhSachBaiViet().size() >0 ){
                    tvThongBao.setVisibility(View.GONE);
                    rvDanhSach.setVisibility(View.VISIBLE);
                    baiVietYeuThichAdapter.setData(response.body().getDanhSachBaiViet());
                }else {
                    tvThongBao.setVisibility(View.VISIBLE);
                    tvThongBao.setText(response.body().getThongBao());
                    rvDanhSach.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadBaiVietYeuThich", "onFailure: "+t.getMessage());

            }
        });
    }

    public void loadMatHangQuanTam(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachQuanTam(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                refreshLayout.setRefreshing(false);
                Log.d("Data", "onResponse: "+response.body().getDanhSachMatHang());
                if(response.body().getDanhSachMatHang().size() >0 ){
                    tvThongBao.setVisibility(View.GONE);
                    rvDanhSach.setVisibility(View.VISIBLE);
                    matHangAdapter.setData(response.body().getDanhSachMatHang());
                }else {
                    tvThongBao.setVisibility(View.VISIBLE);
                    tvThongBao.setText(response.body().getThongBao());
                    rvDanhSach.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadMatHangQuanTam", "onFailure: "+t.getMessage());

            }
        });
    }
}