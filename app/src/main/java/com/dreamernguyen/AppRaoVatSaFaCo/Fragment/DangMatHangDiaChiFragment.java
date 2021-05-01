package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

public class DangMatHangDiaChiFragment extends Fragment {

    TextView tvThanhPho, tvQuan, tvPhuongXa;
    ImageView tvBack;
    AppCompatButton btnToiKhac;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dang_mat_hang_dia_chi, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvBack = view.findViewById(R.id.imgBack);
        tvThanhPho = view.findViewById(R.id.tvThanhPho);
        tvQuan = view.findViewById(R.id.tvQuan);
        tvPhuongXa = view.findViewById(R.id.tvPhuongXa);
        btnToiKhac = view.findViewById(R.id.btnToiKhac);

        layGiaTri();
        hienThiBtn();

        tvBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDiaChiToHinhAnh);
            DangMatHangActivity.viTri = 1;
        });


        tvThanhPho.setOnClickListener(v -> {
            DangMatHangActivity.loadDiaChi = "Tinh";
            DangMatHangActivity.ThanhPho="";
            DangMatHangActivity.QuanHuyen="";
            DangMatHangActivity.PhuongXa="";
            denThongTin();
        });
        tvQuan.setOnClickListener(v -> {
            DangMatHangActivity.loadDiaChi = "Quan";
            DangMatHangActivity.QuanHuyen="";
            DangMatHangActivity.PhuongXa="";
            denThongTin();
        });
        tvPhuongXa.setOnClickListener(v -> {
            DangMatHangActivity.loadDiaChi = "Xa";
            DangMatHangActivity.PhuongXa="";
            denThongTin();
        });

        btnToiKhac.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.actionDiaChiToKhac);
            DangMatHangActivity.viTri = 3;

        });


    }

    @Override
    public void onResume() {

        layGiaTri();
        hienThiBtn();
        super.onResume();
    }

    public void denThongTin() {
        Navigation.findNavController(getActivity(),R.id.nav_host).navigate(R.id.actionDiaChiToThongTin);
        DangMatHangActivity.viTri = 2;
    }


    public void layGiaTri() {
        tvThanhPho.setText(DangMatHangActivity.ThanhPho);
        tvQuan.setText(DangMatHangActivity.QuanHuyen);
        tvPhuongXa.setText(DangMatHangActivity.PhuongXa);

    }

    public void hienThiBtn() {

        if (DangMatHangActivity.ThanhPho != null && DangMatHangActivity.QuanHuyen != null && !DangMatHangActivity.ThanhPho.isEmpty() && !DangMatHangActivity.QuanHuyen.isEmpty()) {
            btnToiKhac.setVisibility(View.VISIBLE);

        } else {
            btnToiKhac.setVisibility(View.GONE);

        }

    }

}
