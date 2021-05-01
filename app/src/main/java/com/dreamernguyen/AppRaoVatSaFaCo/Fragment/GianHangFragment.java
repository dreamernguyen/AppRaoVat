package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TimKiemActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.DanhMucGianHangAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.MatHangAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.BaiViet;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GianHangFragment extends Fragment {

    MaterialButton btnTimKiemNhanh, btnDangSanPham;
    RecyclerView rvDanhMuc, rvDanhSach;
    ImageView imgAvatar;
    LinearLayout lnThemMatHang;
    DanhMucGianHangAdapter danhMucGianHangAdapter;
    MatHangAdapter matHangAdapter;
    LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gian_hang, container, false);
        layDuLieu();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTimKiemNhanh = view.findViewById(R.id.btnTimKiemNhanh);
        btnDangSanPham = view.findViewById(R.id.btnDangSanPham);
        rvDanhMuc = view.findViewById(R.id.rvDanhMuc);
        rvDanhSach = view.findViewById(R.id.rvDanhSach);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        lnThemMatHang = view.findViewById(R.id.lnThemMatHang);
        layout = view.findViewById(R.id.layout);
        rvDanhSach.setNestedScrollingEnabled(false);

        datRvDanhMuc();
        layDuLieu();
        nhanNut();

    }

    public void nhanNut() {
        btnDangSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LocalDataManager.getNguoiDung().getSoDienThoai() != null && !LocalDataManager.getNguoiDung().getSoDienThoai().isEmpty()){
                    Intent intent = new Intent(getActivity(), DangMatHangActivity.class);
                    startActivity(intent);

                }
                else {
                    Snackbar snackbar = Snackbar.make(layout, "Thêm số điện thoại đi", BaseTransientBottomBar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        btnTimKiemNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public void datRvDanhMuc(){
        List<String> list = new ArrayList<>(Arrays.asList("Bất Động Sản", "Xe Cộ", "Sách", "Đồ Điện Tử", "Thời Trang"));
        danhMucGianHangAdapter= new DanhMucGianHangAdapter(getContext(),list);
        rvDanhMuc.setAdapter(danhMucGianHangAdapter);
        rvDanhMuc.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
    }

    public void datRvDanhSach(List<MatHang> list){

        matHangAdapter= new MatHangAdapter(getActivity());
        matHangAdapter.setData(list);
        rvDanhSach.setAdapter(matHangAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvDanhSach.setLayoutManager(staggeredGridLayoutManager);
    }

    public void layDuLieu(){

        Call<DuLieuTraVe> call = ApiService.apiService.danhSachMatHang();
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                List<MatHang> list = response.body().getDanhSachMatHang();
                datRvDanhSach(list);
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }

        });

    }

    public void kiemTraTimKiemTemp(){

        TimKiem timKiem = TimKiemDataBase.getInstance(getActivity()).timKiemDAO().findTemp("DangTao");

        if (timKiem!=null){
            Toast.makeText(getActivity(), "co da xoa", Toast.LENGTH_SHORT).show();
            TimKiemDataBase.getInstance(getActivity()).timKiemDAO().deleteTemp("DangTao");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        layDuLieu();
        kiemTraTimKiemTemp();
    }
}