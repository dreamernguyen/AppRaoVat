package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.NguoiDung;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChinhSuaThongTinActivity extends AppCompatActivity {
    TextInputLayout layoutHoTen,layoutSDT,layoutEmail,layoutDiaChi,layoutTieuSu,layoutNgaySinh,layoutGioiTinh,layoutMatKhau,layoutMatKhauMoi;
    TextInputEditText edHoTen,edSDT,edEmail,edDiaChi,edTieuSu,edNgaySinh,edGioiTinh,edMatKhau,edMatKhauMoi;
    MaterialButton btnChinhSua,btnDoiMatKhau;
    String matKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thong_tin);

        layoutHoTen = findViewById(R.id.layoutHoTen);
        layoutDiaChi = findViewById(R.id.layoutDiaChi);
        layoutSDT = findViewById(R.id.layoutSDT);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutTieuSu = findViewById(R.id.layoutTieuSu);
        layoutNgaySinh = findViewById(R.id.layoutNgaySinh);
        layoutGioiTinh = findViewById(R.id.layoutGioiTinh);
        layoutMatKhau = findViewById(R.id.layoutMatKhau);
        layoutMatKhauMoi =findViewById(R.id.layoutMatKhauMoi);

        edHoTen = findViewById(R.id.edHoTen);
        edDiaChi = findViewById(R.id.edDiaChi);
        edSDT = findViewById(R.id.edSDT);
        edEmail = findViewById(R.id.edEmail);
        edTieuSu = findViewById(R.id.edTieuSu);
        edNgaySinh = findViewById(R.id.edNgaySinh);
        edGioiTinh = findViewById(R.id.edGioiTinh);
        edMatKhau = findViewById(R.id.edMatKhau);
        edMatKhauMoi =findViewById(R.id.edMatKhauMoi);

        btnChinhSua = findViewById(R.id.btnChinhSua);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);

        Intent i = getIntent();
        if(i.getExtras()!= null){
            if(i.getStringExtra("ChucNang").equals("DoiMatKhau")){
                layoutMatKhau.setVisibility(View.VISIBLE);
                layoutMatKhauMoi.setVisibility(View.VISIBLE);
                btnDoiMatKhau.setVisibility(View.VISIBLE);
                btnChinhSua.setVisibility(View.GONE);
                layoutHoTen.setVisibility(View.GONE);
                layoutDiaChi.setVisibility(View.GONE);
                layoutSDT.setVisibility(View.GONE);
                layoutEmail.setVisibility(View.GONE);
                layoutTieuSu.setVisibility(View.GONE);
                layoutNgaySinh.setVisibility(View.GONE);
                layoutGioiTinh.setVisibility(View.GONE);
            }else{
                layoutHoTen.setVisibility(View.VISIBLE);
                layoutDiaChi.setVisibility(View.VISIBLE);
                layoutSDT.setVisibility(View.VISIBLE);
                layoutEmail.setVisibility(View.VISIBLE);
                layoutTieuSu.setVisibility(View.VISIBLE);
                layoutNgaySinh.setVisibility(View.VISIBLE);
                layoutGioiTinh.setVisibility(View.VISIBLE);
                btnChinhSua.setVisibility(View.VISIBLE);
                layoutMatKhau.setVisibility(View.GONE);
                layoutMatKhauMoi.setVisibility(View.GONE);
                btnDoiMatKhau.setVisibility(View.GONE);
            }
        }
        loadThongTin();
    }

    public void loadThongTin(){
        Call<DuLieuTraVe> call = ApiService.apiService.thongTinNguoiDung(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                NguoiDung nguoiDung = response.body().getNguoiDung();
                edHoTen.setText(nguoiDung.getHoTen());
                edDiaChi.setText(nguoiDung.getDiaChi());
                edSDT.setText(nguoiDung.getSoDienThoai());
                edEmail.setText(nguoiDung.getEmail());
                edTieuSu.setText(nguoiDung.getTieuSu());
                edNgaySinh.setText(nguoiDung.getNgaySinh());
                matKhau = nguoiDung.getMatKhau();
                edGioiTinh.setText(nguoiDung.getGioiTinh());
                edSDT.setText(nguoiDung.getSoDienThoai());
                edEmail.setText(nguoiDung.getEmail());
                btnChinhSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(btnChinhSua.getText().toString().equals("Chỉnh sửa")){
                            btnChinhSua.setText("Cập nhật");
                            edHoTen.setEnabled(true);
                            edDiaChi.setEnabled(true);
                            edTieuSu.setEnabled(true);
                            edNgaySinh.setEnabled(true);
                            edGioiTinh.setEnabled(true);
                        }else {
                            btnChinhSua.setText("Chỉnh sửa");
                            edHoTen.setEnabled(false);
                            edDiaChi.setEnabled(false);
                            edTieuSu.setEnabled(false);
                            edNgaySinh.setEnabled(false);
                            edGioiTinh.setEnabled(false);
                            chinhSuaThongTin();
                        }

                    }
                });
                if(nguoiDung.getMatKhau() != null){
                    btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            doiMatKhau();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadTrangCaNhan", "onFailure: " + t.getMessage());
                Toast.makeText(ChinhSuaThongTinActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void chinhSuaThongTin(){
        NguoiDung nguoiDung = new NguoiDung(edHoTen.getText().toString(),edNgaySinh.getText().toString(),edDiaChi.getText().toString(),edTieuSu.getText().toString(),edGioiTinh.getText().toString());
        Call<DuLieuTraVe> call = ApiService.apiService.chinhSuaThongTin(LocalDataManager.getIdNguoiDung(),nguoiDung);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                Toast.makeText(ChinhSuaThongTinActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadTrangCaNhan", "onFailure: " + t.getMessage());
                Toast.makeText(ChinhSuaThongTinActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void doiMatKhau(){
        Call<DuLieuTraVe> call = ApiService.apiService.doiMatKhau(LocalDataManager.getIdNguoiDung(),edMatKhau.getText().toString(),edMatKhauMoi.getText().toString());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                if(response.body().getThongBao().equals("Mật khẩu cũ không trùng khớp !")){
                    layoutMatKhau.setError(response.body().getThongBao());
                }else {
                    Toast.makeText(ChinhSuaThongTinActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("doiMatKhau", "onFailure: " + t.getMessage());
                Toast.makeText(ChinhSuaThongTinActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}