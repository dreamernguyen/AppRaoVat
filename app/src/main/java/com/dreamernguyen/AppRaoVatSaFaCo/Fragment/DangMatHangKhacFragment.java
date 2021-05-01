package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.textfield.TextInputEditText;

public class DangMatHangKhacFragment extends Fragment {

    ImageView tvBack;
    EditText edTieuDe, edNoiDung;
    TextInputEditText edGia;
    Button btn, btn1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_mat_hang_khac, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvBack = view.findViewById(R.id.imgBack);

        edGia = view.findViewById(R.id.edGia);
        edTieuDe = view.findViewById(R.id.edTieuDe);
        edNoiDung = view.findViewById(R.id.edNoiDung);

        btn1 = view.findViewById(R.id.btn1);

        layThongTin();
        hienThiBtn();

        edGia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                hienThiBtn();
                if(s.length() >0 ){
                    DangMatHangActivity.giaBan = Integer.parseInt(s.toString())*1000;
                }else {
                    DangMatHangActivity.giaBan = 0;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edTieuDe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DangMatHangActivity.tieuDe = edTieuDe.getText().toString();

                hienThiBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edNoiDung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hienThiBtn();
                DangMatHangActivity.noiDung = edNoiDung.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionKhacToDiaChi);
            DangMatHangActivity.viTri = 2;
        });


        btn1.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.actionKhacToDangMatHangFragment);
            DangMatHangActivity.viTri = 4;
        });
    }

    @Override
    public void onResume() {
        layThongTin();
        hienThiBtn();
        super.onResume();
    }

    public void hienThiBtn() {
        if (DangMatHangActivity.giaBan != 0 && DangMatHangActivity.tieuDe != null && !DangMatHangActivity.tieuDe.isEmpty() && DangMatHangActivity.noiDung != null && !DangMatHangActivity.noiDung.isEmpty()) {
            btn1.setVisibility(View.VISIBLE);
        } else {
            btn1.setVisibility(View.GONE);
        }
    }

    public void layThongTin() {
        edTieuDe.setText(DangMatHangActivity.tieuDe);
        edNoiDung.setText(DangMatHangActivity.noiDung);
        if((DangMatHangActivity.giaBan/ 1000) > 0){
            edGia.setText(DangMatHangActivity.giaBan/ 1000 + "");
        }
        edGia.setHint("Nhập giá bán");
    }
}
