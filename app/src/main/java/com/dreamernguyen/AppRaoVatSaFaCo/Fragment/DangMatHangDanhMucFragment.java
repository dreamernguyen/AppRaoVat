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
import androidx.navigation.Navigation;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

public class DangMatHangDanhMucFragment extends Fragment {

    TextView tvDanhMuc, tvDanhMucCon ;
    AppCompatButton btn;
    ImageView tvBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DangMatHangActivity) getActivity()).setFragmentRefreshListener(new DangMatHangActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                layGiaTri();
                hienThiBtn();
                nhanDanhMucCon();
            }
        });
        return inflater.inflate(R.layout.fragment_dang_mat_hang_danh_muc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tvBack = view.findViewById(R.id.imgBack);
        tvDanhMuc = view.findViewById(R.id.tvDanhMuc);
        tvDanhMucCon = view.findViewById(R.id.tvDanhMucCon);
        btn = view.findViewById(R.id.btn);

        layGiaTri();
        hienThiBtn();
        nhanDanhMucCon();


        btn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDanhMucToHinhAnh);
            DangMatHangActivity.viTri = 1;
            btn.setVisibility(View.GONE);
        });

        tvDanhMuc.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDanhMucToThongTin);
            denThongTin();
            DangMatHangActivity.DanhMuc = "";
            DangMatHangActivity.DanhMucCon = "";

        });

        tvDanhMucCon.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDanhMucToThongTin);
            denThongTin();
            DangMatHangActivity.DanhMucCon = "";

        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        layGiaTri();
        hienThiBtn();
        nhanDanhMucCon();
        super.onResume();
    }

    public void layGiaTri() {
        tvDanhMuc.setText(DangMatHangActivity.DanhMuc);
        tvDanhMucCon.setText(DangMatHangActivity.DanhMucCon);
    }

    public void hienThiBtn() {

        if (DangMatHangActivity.DanhMuc != null && !DangMatHangActivity.DanhMuc.isEmpty()) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    public void denThongTin() {
        btn.setVisibility(View.GONE);
        DangMatHangActivity.viTri = 0;
    }

    public void nhanDanhMucCon() {
        if (DangMatHangActivity.DanhMuc != null && !DangMatHangActivity.DanhMuc.isEmpty()) {
            tvDanhMucCon.setEnabled(true);
        } else {
            tvDanhMucCon.setEnabled(false);
        }
    }
}
