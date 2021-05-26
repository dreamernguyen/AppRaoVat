package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.TabLayoutAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.TabLayoutMatHangAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatHangToiRaoActivity extends AppCompatActivity {
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    private static TabLayoutMatHangAdapter tabLayoutAdapter;

    public static List<MatHang> listDangRao = new ArrayList<>();
    public static List<MatHang> listChoDuyet = new ArrayList<>();
    public static List<MatHang> listDaAn = new ArrayList<>();
    TextView tvTrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang_toi_rao);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        tvTrong = findViewById(R.id.tvTrong);
        tabLayout = findViewById(R.id.tab_layout_mat_hang);
        viewPager = findViewById(R.id.mat_hang_viewpager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black_overlay),getResources().getColor(R.color.main_pink));
        tabLayoutAdapter = new TabLayoutMatHangAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        loadMatHangToiRao();


    }
    public void loadMatHangToiRao(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachToiBan(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                List<MatHang> listMatHang = response.body().getDanhSachMatHang();
                if(listMatHang.size() > 0){
                    tvTrong.setVisibility(View.GONE);
                    for(int i = 0; i < listMatHang.size();i++){
                        if(listMatHang.get(i).getDaDuyet() == true && listMatHang.get(i).getDaAn() == false) {
                            listDangRao.add(listMatHang.get(i));
                        }else if(listMatHang.get(i).getDaAn() == true){
                            listDaAn.add(listMatHang.get(i));
                        }else {
                            listChoDuyet.add(listMatHang.get(i));
                        }
                    }
                    tabLayoutAdapter.setNumber(listDangRao.size(),listChoDuyet.size(),listDaAn.size());
                    tabLayout.setupWithViewPager(viewPager);
                    viewPager.setAdapter(tabLayoutAdapter);
                }else {
                    tvTrong.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.GONE);
                    tvTrong.setText("Bạn chưa rao mặt hàng nào !");
                }

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadMatHangToiRao", "onFailure: "+t.getMessage());
            }
        });
    }

    public static void reloadMatHangToiRao(int pos){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachToiBan(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                listDangRao.clear();
                listChoDuyet.clear();
                listDaAn.clear();
                List<MatHang> listMatHang = response.body().getDanhSachMatHang();
                if(listMatHang.size() > 0){
                    for(int i = 0; i < listMatHang.size();i++){
                        if(listMatHang.get(i).getDaDuyet() == true && listMatHang.get(i).getDaAn() == false) {
                            listDangRao.add(listMatHang.get(i));
                        }else if(listMatHang.get(i).getDaAn()){
                            listDaAn.add(listMatHang.get(i));
                        }else {
                            listChoDuyet.add(listMatHang.get(i));
                        }
                    }
                    tabLayoutAdapter.setNumber(listDangRao.size(),listChoDuyet.size(),listDaAn.size());
                    tabLayout.setupWithViewPager(viewPager);
                    viewPager.setAdapter(tabLayoutAdapter);
                    viewPager.setCurrentItem(pos);
                }

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadMatHangToiRao", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listDangRao.clear();
        listChoDuyet.clear();
        listDaAn.clear();
    }
}