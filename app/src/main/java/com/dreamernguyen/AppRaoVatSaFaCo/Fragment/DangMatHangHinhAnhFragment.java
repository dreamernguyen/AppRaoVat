package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.MatHangHinhAnhAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class DangMatHangHinhAnhFragment extends Fragment {
    RecyclerView rvHinhAnh;
    MatHangHinhAnhAdapter matHangHinhAnhAdapter;
    List<String> URIString;
    Button btnToiDiaChi;
    TextView tv, tvThemAnh;
    ImageView tvBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_mat_hang_hinh_anh, container, false);

        tvBack = view.findViewById(R.id.imgBack);

        tvThemAnh = view.findViewById(R.id.tvThemAnh);
        btnToiDiaChi = view.findViewById(R.id.btnToiDiaChi);
        rvHinhAnh = view.findViewById(R.id.rvHinhAnh);

        layThongTin();

        Log.d("TAGa", "uri dang: " + DangMatHangActivity.listURI);
        Log.d("TAGa", "anh dang: " + DangMatHangActivity.listAnh);
        Log.d("TAGa", "string dang: " + DangMatHangActivity.listString);

        hienThiBtn();

        matHangHinhAnhAdapter = new MatHangHinhAnhAdapter(getActivity());
        matHangHinhAnhAdapter.setListAnh(DangMatHangActivity.listAnh);



        tvBack.setOnClickListener(va -> {
            Navigation.findNavController(va).navigate(R.id.actionHinhAnhToDanhMuc);
            DangMatHangActivity.viTri = 0;
        });
        tvThemAnh.setOnClickListener(v -> TedBottomPicker.with(getActivity()).setSelectedUriList(DangMatHangActivity.listURI).showMultiImage(uriList -> {

            DangMatHangActivity.listURI = uriList;
            layThongTin();
            matHangHinhAnhAdapter.setListAnh(DangMatHangActivity.listAnh);
            hienThiBtn();


            rvHinhAnh.setAdapter(matHangHinhAnhAdapter);
            rvHinhAnh.setLayoutManager(new GridLayoutManager(getContext(), 3));

            Log.d("TAGa", "uri dang: " + DangMatHangActivity.listURI);
            Log.d("TAGa", "anh dang: " + DangMatHangActivity.listAnh);
            Log.d("TAGa", "string dang: " + DangMatHangActivity.listString);
        }));
        btnToiDiaChi.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionHinhAnhToDiaChi);
            DangMatHangActivity.viTri = 2;
        });
        rvHinhAnh.setAdapter(matHangHinhAnhAdapter);
        rvHinhAnh.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    public void hienThiBtn() {

        if (DangMatHangActivity.listAnh != null && !DangMatHangActivity.listAnh.isEmpty()) {
            btnToiDiaChi.setVisibility(View.VISIBLE);
        } else {

            btnToiDiaChi.setVisibility(View.GONE);

        }
    }

    public void layThongTin() {


        URIString = new ArrayList<>();
        for (int i = 0; i < DangMatHangActivity.listURI.size(); i++) {
            URIString.add(DangMatHangActivity.listURI.get(i).toString());
        }

        if (!DangMatHangActivity.listString.isEmpty()) {
            URIString.addAll(DangMatHangActivity.listString);
        }
        DangMatHangActivity.listAnh = URIString;
    }
}
