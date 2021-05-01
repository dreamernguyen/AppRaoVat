package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.TabLayoutMatHangAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.TabLayoutNguoiDungAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.NguoiDung;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NguoiTheoDoiActivity extends AppCompatActivity {
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    private static TabLayoutNguoiDungAdapter tabLayoutAdapter;
    private static String idNguoiDung;
    int position;

    public static List<NguoiDung> listDangTheoDoi = new ArrayList<>();
    public static List<NguoiDung> listDuocTheoDoi = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_theo_doi);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        Intent i = getIntent();
        if(i.getExtras() != null){
            idNguoiDung = i.getStringExtra("idNguoiDung");
            position = i.getIntExtra("pos",0);
        }
        tabLayout = findViewById(R.id.tab_layout_nguoi_dung);
        viewPager = findViewById(R.id.nguoi_dung_viewpager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black_overlay),getResources().getColor(R.color.main_pink));
        tabLayoutAdapter = new TabLayoutNguoiDungAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        loadNguoiTheoDoi();

    }

    public void loadNguoiTheoDoi(){
        Call<DuLieuTraVe> call = ApiService.apiService.xemNguoiTheoDoi(idNguoiDung);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                listDangTheoDoi = response.body().getDangTheoDoi();
                listDuocTheoDoi = response.body().getDuocTheoDoi();
                tabLayoutAdapter.setNumber(listDuocTheoDoi.size(),listDangTheoDoi.size());
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setAdapter(tabLayoutAdapter);
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadNguoiTheoDoi", "onFailure: "+t.getMessage());
            }
        });
    }
    public static void reloadNguoiTheoDoi(int pos){
        listDangTheoDoi.clear();
        listDuocTheoDoi.clear();
        Call<DuLieuTraVe> call = ApiService.apiService.xemNguoiTheoDoi(idNguoiDung);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                listDangTheoDoi = response.body().getDangTheoDoi();
                listDuocTheoDoi = response.body().getDuocTheoDoi();
                tabLayoutAdapter.setNumber(listDuocTheoDoi.size(),listDangTheoDoi.size());
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setAdapter(tabLayoutAdapter);
                viewPager.setCurrentItem(pos);
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadNguoiTheoDoi", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listDangTheoDoi.clear();
        listDuocTheoDoi.clear();
    }

}