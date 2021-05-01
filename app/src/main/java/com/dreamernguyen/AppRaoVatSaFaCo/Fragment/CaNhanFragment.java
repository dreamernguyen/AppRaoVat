package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.ChinhSuaThongTinActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangBaiActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangNhapActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DanhSachActivity;
//import com.dreamernguyen.AppRaoVatSaFaCo.Activity.ChinhSuaThongTinActivity;
//import com.dreamernguyen.AppRaoVatSaFaCo.Activity.MatHangQuanTamActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.MatHangToiRaoActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TrangCaNhanActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaNhanFragment extends Fragment {
    MaterialCardView cvXemTrangCaNhan;
    MaterialButton btnBaiVietYeuThich,btnMatHangToiRao,btnMatHangDaLuu,btnBaiVietDaAn,btnChinhSuaThongTin,btnDoiMatKhau,btnDangXuat;
    ImageView imgAvatar, capNhatAvatar;
    TextView tvTenNguoiDung;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);

        cvXemTrangCaNhan = view.findViewById(R.id.cvXemTrangCaNhan);
        btnBaiVietYeuThich = view.findViewById(R.id.btnBaiVietYeuThich);
        btnMatHangToiRao = view.findViewById(R.id.btnMatHangToiRao);
        btnMatHangDaLuu = view.findViewById(R.id.btnMatHangDaLuu);
        btnBaiVietDaAn = view.findViewById(R.id.btnBaiVietDaAn);
        btnChinhSuaThongTin = view.findViewById(R.id.btnChinhSuaThongTin);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        tvTenNguoiDung = view.findViewById(R.id.tvTenNguoiDung);
        capNhatAvatar = view.findViewById(R.id.capNhatAvatar);


        Glide.with(getContext()).load(LocalDataManager.getNguoiDung().getAvatar()).into(imgAvatar);
        tvTenNguoiDung.setText(LocalDataManager.getNguoiDung().getHoTen());
        cvXemTrangCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TrangCaNhanActivity.class);
                i.putExtra("idNguoiDung",LocalDataManager.getIdNguoiDung());
                startActivity(i);
            }
        });
        btnBaiVietYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DanhSachActivity.class);
                i.putExtra("ChucNang","BaiVietYeuThich");
                startActivity(i);
            }
        });
        btnChinhSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChinhSuaThongTinActivity.class);
                i.putExtra("ChucNang","SuaThongTin");
                startActivity(i);
            }
        });
        btnMatHangToiRao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MatHangToiRaoActivity.class);
                startActivity(i);
            }
        });
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LocalDataManager.getNguoiDung().getMatKhau() != null){
                    Intent i = new Intent(getActivity(), ChinhSuaThongTinActivity.class);
                    i.putExtra("ChucNang","DoiMatKhau");
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(), "Bạn chưa có mật khẩu vì sử dụng đăng nhập nhanh bằng Google!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnMatHangDaLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DanhSachActivity.class);
                i.putExtra("ChucNang","MatHangQuanTam");
                startActivity(i);
            }
        });
        btnBaiVietDaAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DanhSachActivity.class);
                i.putExtra("ChucNang","BaiVietDaAn");
                startActivity(i);
            }
        });
        capNhatAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guiAnh();
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDataManager.setIdNguoiDung("");
                LocalDataManager.setNguoiDung(null);
                Intent i = new Intent(getActivity(), DangNhapActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return view;
    }
    private void guiAnh() {
        TedBottomPicker.with(getActivity())
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Xác nhận")
                .setEmptySelectionText("Không ảnh nào được chọn")
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        MediaManager.get().upload(uri)
                                .unsigned("anhNguoiDung").callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                String linkAnh = resultData.get("url").toString();
                                Call<DuLieuTraVe> call = ApiService.apiService.capNhatAvatar(LocalDataManager.getIdNguoiDung(),linkAnh);
                                call.enqueue(new Callback<DuLieuTraVe>() {
                                    @Override
                                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                        Toast.makeText(getContext(), response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                        Toast.makeText(getContext(), "Cập nhật avatar thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            @Override
                            public void onError(String requestId, ErrorInfo error) {

                            }
                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {

                            }
                        }).dispatch();
                    }
                });
    }

}