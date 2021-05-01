package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {
    TextInputLayout layoutSDT, layoutHoTen, layoutMatKhau, layoutNhapLaiMatKhau;
    TextInputEditText edHoTen, edSDT, edMatKhau, edNhapLaiMatKhau;
    TextView btnDangNhap;
    MaterialButton btnDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        // ánh xạ
        edHoTen = findViewById(R.id.edHoTen);
        edSDT = findViewById(R.id.edSDT);
        edMatKhau = findViewById(R.id.edMatKhau);
        edNhapLaiMatKhau = findViewById(R.id.edNhapLaiMatKhau);
        layoutSDT = findViewById(R.id.layoutSDT);
        layoutMatKhau = findViewById(R.id.layoutMatKhau);
        layoutHoTen = findViewById(R.id.layoutHoTen);
        layoutNhapLaiMatKhau = findViewById(R.id.layoutNhapLaiMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangNhap = findViewById(R.id.tvDangNhap);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DangKyActivity.this,DangNhapActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
                if (layoutSDT.getError() == null & layoutMatKhau.getError() == null & layoutHoTen.getError() == null & layoutNhapLaiMatKhau.getError() == null) {
                    String mHoTen, mMatKhau, mSDT;
                    mHoTen = edHoTen.getText().toString().trim();
                    mMatKhau = edMatKhau.getText().toString().trim();
                    mSDT = edSDT.getText().toString().trim();
                    Call<DuLieuTraVe> call = ApiService.apiService.checkSoDienThoai(mSDT);
                    call.enqueue(new Callback<DuLieuTraVe>() {
                        @Override
                        public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                            if(response.body().getThongBao().equals("Số điện thoại này đã được đăng ký")){
                                layoutSDT.setError(response.body().getThongBao());
                            }else {
                                Intent i = new Intent(getApplicationContext(),XacThucActivity.class);
                                i.putExtra("SDT",mSDT);
                                i.putExtra("hoTen",mHoTen);
                                i.putExtra("matKhau",mMatKhau);
                                startActivity(i);
                            }
                        }
                        @Override
                        public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                            Log.d("checkSoDienThoai", "onFailure: "+t.getMessage());
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra lại thông tin đăng ký !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edSDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutSDT.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutMatKhau.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edHoTen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutHoTen.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edNhapLaiMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutNhapLaiMatKhau.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void validateForm() {
        if (edSDT.getText().toString().trim().isEmpty()) {
            layoutSDT.setError("Không được để trống !");
        }
        if (edHoTen.getText().toString().trim().isEmpty()) {
            layoutHoTen.setError("Không được để trống !");
        }
        if (edMatKhau.getText().toString().trim().isEmpty()) {
            layoutMatKhau.setError("Không được để trống !");
        }
        if (edNhapLaiMatKhau.getText().toString().trim().isEmpty()) {
            layoutNhapLaiMatKhau.setError("Không được để trống !");
        }
        if (!edMatKhau.getText().toString().equals(edNhapLaiMatKhau.getText().toString())) {
            layoutNhapLaiMatKhau.setError("Nhập lại mật khẩu không khớp !");
        }
        if (edSDT.getText().toString().length() != 10) {
            layoutSDT.setError("Vui lòng nhập số điện thoại có 10 số !");
        }
    }
}