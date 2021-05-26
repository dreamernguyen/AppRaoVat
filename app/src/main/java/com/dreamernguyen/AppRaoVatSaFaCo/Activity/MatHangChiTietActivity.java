package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.AnhAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.AnhBaiVietAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.NguoiDungAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatHangChiTietActivity extends AppCompatActivity {

    ViewPager vpgImage;
    TextView tvTieuDe, tvGiaBan, tvDiaChi, tvMota, tvThoiGianChiTiet, tvHoTen, tvSdt,tvTieuDe2, tvGiaBan2, tvDiaChi2, tvMota2, tvHoTen2, tvSdt2,tvThoiGianChiTiet2;
    String idMatHangChiTiet;
    Intent intent;
    MaterialCheckBox chkLuuTin;
    CircleImageView imgAvatar;
    LinearLayout layout1,layout2, lnNguoiMua;
    MaterialButton btnSuaTin,btnXoaTin,btnBaoCao,btnDanhSachQuanTam,btnNhanTin,btnGoiDien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang_chi_tiet);

        initView();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        loadBundle();
        btnSuaTin.setOnClickListener(v -> {
            intent= new Intent(this,DangMatHangActivity.class);
            intent.putExtra("idMatHangChiTiet",idMatHangChiTiet);
            startActivity(intent);
        });

        btnXoaTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<DuLieuTraVe> call= ApiService.apiService.xoaMatHang(idMatHangChiTiet);
                call.enqueue(new Callback<DuLieuTraVe>() {
                    @Override
                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                        finish();
                    }

                    @Override
                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

                    }
                });
            }
        });


        Call<DuLieuTraVe> call = ApiService.apiService.xemChiTietMatHang(idMatHangChiTiet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                MatHang matHang = response.body().getMatHang();
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
                if(matHang.getIdNguoiDung().getId().equals(LocalDataManager.getIdNguoiDung())){
                    btnSuaTin.setVisibility(View.VISIBLE);
                    btnXoaTin.setVisibility(View.VISIBLE);
                    btnDanhSachQuanTam.setVisibility(View.VISIBLE);
                    lnNguoiMua.setVisibility(View.GONE);
                    chkLuuTin.setVisibility(View.GONE);
                    btnBaoCao.setVisibility(View.GONE);

                }else {
                    btnSuaTin.setVisibility(View.GONE);
                    btnXoaTin.setVisibility(View.GONE);
                    btnDanhSachQuanTam.setVisibility(View.GONE);
                    lnNguoiMua.setVisibility(View.VISIBLE);
                    chkLuuTin.setVisibility(View.VISIBLE);
                    btnBaoCao.setVisibility(View.VISIBLE);
                }
                Dialog dialog = new Dialog(MatHangChiTietActivity.this,R.style.BottomSheetThemeCustom);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_bao_cao_mat_hang);

                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAttributes);
                dialog.setCancelable(false);

                MaterialButton btnYes = dialog.findViewById(R.id.btnYes);
                MaterialButton btnNo = dialog.findViewById(R.id.btnNo);
                ChipGroup chipGroup = dialog.findViewById(R.id.chipGroup);
                List<String> a = new ArrayList<>();

                chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ChipGroup group, int checkedId) {
                        Chip chip = dialog.findViewById(checkedId);
                        a.clear();
                        if(chip != null){
                            a.add(chip.getText().toString());
                        }
                    }
                });

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(a.size() > 0){
                            Call<DuLieuTraVe> call = ApiService.apiService.baoCaoMatHang(matHang.getId(),LocalDataManager.getIdNguoiDung(),a.get(0).toString());
                            call.enqueue(new Callback<DuLieuTraVe>() {
                                @Override
                                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(), "Vui lòng chọn lí do báo cáo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnBaoCao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                    }
                });
                // khởi tạo
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                format.setTimeZone(TimeZone.getTimeZone("UTC+7"));
                Calendar cal = Calendar.getInstance();
                Date now = cal.getTime();

                // Xử lí dữ liệu
                String mLinkAnh = response.body().getMatHang().getIdNguoiDung().getAvatar();
                List<String> listAnh = response.body().getMatHang().getLinkAnh();
                if (listAnh.size() > 0){
                    AnhBaiVietAdapter anhBaiVietAdapter = new AnhBaiVietAdapter(listAnh, false);
                    vpgImage.setAdapter(anhBaiVietAdapter);
                }
                try {
                    Date date = format.parse(response.body().getMatHang().getThoiGianTao());
                    long diff = now.getTime() - date.getTime();
                    long giay =  (diff / 1000);
                    long phut =  (diff / (1000 * 60));
                    long gio =  (diff / (1000 * 60 * 60));
                    long ngay =  (diff / (1000 * 60 * 60 * 24));
                    if(ngay > 3){
                        tvThoiGianChiTiet.setText(format2.format(date));
                    }
                    if (ngay < 3){
                        tvThoiGianChiTiet.setText(ngay + "ngày trước");
                    }
                    if( gio < 24 && gio > 0){
                        tvThoiGianChiTiet.setText(gio + " giờ trước");
                    }if(phut < 60 && phut > 0){
                        tvThoiGianChiTiet.setText(phut+ " phút trước");
                    }if(giay < 60 && giay > 0){
                        tvThoiGianChiTiet.setText("vừa xong");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // gán dữ liệu
                tvTieuDe.setText(matHang.getTieuDe());
                tvDiaChi.setText(matHang.getDiaChi());
                tvGiaBan.setText("Giá: "+matHang.getGiaBan() + " Đồng");
                tvMota.setText(matHang.getMoTa());
                tvHoTen.setText(matHang.getIdNguoiDung().getHoTen());
                tvSdt.setText("Số điện thoại: "+matHang.getIdNguoiDung().getSoDienThoai()+"");
                btnGoiDien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + matHang.getIdNguoiDung().getSoDienThoai()));
                        startActivity(intent);
                    }
                });
                btnNhanTin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), NhanTinActivity.class);
                        intent.putExtra("activity", "MatHangChiTiet");
                        intent.putExtra("idNguoiDung", matHang.getIdNguoiDung().getId());
                        intent.putExtra("tenNguoiDung", matHang.getIdNguoiDung().getHoTen());
                        intent.putExtra("noiDung","Tôi rất quan tâm đến  '"+matHang.getTieuDe()+"' của bạn. Chúng ta có thể trao đổi thêm không ?");
                        startActivity(intent);
                    }
                });

                if(matHang.getNguoiQuanTam().size() == 0){
                    chkLuuTin.setChecked(false);
                }else {
                    for(int i = 0; i < matHang.getNguoiQuanTam().size();i++){
                        if(matHang.getNguoiQuanTam().get(i).getId().equals(LocalDataManager.getIdNguoiDung())){
                            chkLuuTin.setChecked(true);
                        }
                    }
                }
                chkLuuTin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            Call<DuLieuTraVe> call = ApiService.apiService.quanTam(matHang.getId(),LocalDataManager.getIdNguoiDung());
                            call.enqueue(new Callback<DuLieuTraVe>() {
                                @Override
                                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                    Toast.makeText(MatHangChiTietActivity.this, "Đã quan tâm", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                    Log.d("QuanTamMatHang", "onFailure: "+t.getMessage());

                                }
                            });
                        }else {
                            Call<DuLieuTraVe> call = ApiService.apiService.boQquanTam(matHang.getId(),LocalDataManager.getIdNguoiDung());
                            call.enqueue(new Callback<DuLieuTraVe>() {
                                @Override
                                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                    Toast.makeText(MatHangChiTietActivity.this, "Bỏ quan tâm", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                    Log.d("BoQuanTamMatHang", "onFailure: "+t.getMessage());

                                }
                            });

                        }
                    }
                });
                Glide.with(getApplicationContext()).load(mLinkAnh).into(imgAvatar);
                BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(MatHangChiTietActivity.this,R.style.BottomSheetThemeCustom);
                View viewDialog2 =   getLayoutInflater().inflate(R.layout.dialog_luot_thich, new LinearLayout(MatHangChiTietActivity.this));
                bottomSheetDialog2.setContentView(viewDialog2);

                RecyclerView rvLuotThich = viewDialog2.findViewById(R.id.rvLuotThich);
                TextView tv = viewDialog2.findViewById(R.id.tvThongBao);
                TextView tv2 = viewDialog2.findViewById(R.id.tv);
                tv2.setText("Danh sách người quan tâm");
                if (matHang.getNguoiQuanTam().size() == 0){
                    tv.setVisibility(View.VISIBLE);
                    tv.setText("Chưa có người quan tâm");
                    rvLuotThich.setVisibility(View.GONE);
                }else {
                    tv.setVisibility(View.GONE);
                    rvLuotThich.setVisibility(View.VISIBLE);
                    btnDanhSachQuanTam.setText("Đã có "+matHang.getNguoiQuanTam().size()+ " người quan tâm đến mặt hàng của bạn ! Xem ngay là ai nào !");
                }
                rvLuotThich.setLayoutManager(new LinearLayoutManager(MatHangChiTietActivity.this,RecyclerView.VERTICAL,false));
                NguoiDungAdapter nguoiDungAdapter = new NguoiDungAdapter(MatHangChiTietActivity.this);
                rvLuotThich.setAdapter(nguoiDungAdapter);
                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
                rvLuotThich.addItemDecoration(itemDecoration);
                nguoiDungAdapter.setData(matHang.getNguoiQuanTam());

                btnDanhSachQuanTam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog2.show();
                    }
                });

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadMHChitiet", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        Call<DuLieuTraVe> call = ApiService.apiService.xemChiTietMatHang(idMatHangChiTiet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                MatHang matHang = response.body().getMatHang();
                tvTieuDe.setText(matHang.getTieuDe());
                tvDiaChi.setText(matHang.getDiaChi());
                tvGiaBan.setText("Giá: "+matHang.getGiaBan() + " VNĐ");
                tvMota.setText(matHang.getMoTa());
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadMHChiTiet", "onFailure: "+t.getMessage());

            }
        });

        super.onResume();
    }

    public void initView(){

        vpgImage = findViewById(R.id.vpgImage);
        chkLuuTin = findViewById(R.id.chkLuuTin);
        tvTieuDe = findViewById(R.id.tvTieuDeChiTiet);
        tvGiaBan = findViewById(R.id.tvGiaChiTiet);
        tvDiaChi = findViewById(R.id.tvDiaChiChiTiet);
        tvMota = findViewById(R.id.tvNoiDungChiTiet);
        tvThoiGianChiTiet = findViewById(R.id.tvThoiGianChiTiet);
        tvSdt = findViewById(R.id.tvSdt);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnSuaTin=findViewById(R.id.btnSuaTin);
        btnDanhSachQuanTam = findViewById(R.id.btnDanhSachQuanTam);
        btnXoaTin=findViewById(R.id.btnXoaTin);
        btnBaoCao = findViewById(R.id.btnBaoCao);
        tvHoTen = findViewById(R.id.tvHoTen);
        btnNhanTin = findViewById(R.id.btnNhanTin);
        btnGoiDien = findViewById(R.id.btnGoiDien);

        tvTieuDe2 = findViewById(R.id.tvTieuDeChiTiet2);
        tvGiaBan2 = findViewById(R.id.tvGiaChiTiet2);
        tvDiaChi2 = findViewById(R.id.tvDiaChiChiTiet2);
        tvMota2 = findViewById(R.id.tvNoiDungChiTiet2);
        tvThoiGianChiTiet2 = findViewById(R.id.tvThoiGianChiTiet2);
        tvSdt2 = findViewById(R.id.tvSdt2);
        tvHoTen2 = findViewById(R.id.tvHoTen2);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        lnNguoiMua = findViewById(R.id.lnNguoiMua);

    }
    public void loadBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("activity").equals("Adapter")) {
            tvTieuDe2.setText(bundle.getString("tieuDe"));
            idMatHangChiTiet = bundle.getString("idMatHang");
            tvDiaChi2.setText(bundle.getString("diaChi"));
            tvGiaBan2.setText("Giá: " + bundle.getInt("giaBan") + " Đồng");
            tvMota2.setText(bundle.getString("moTa"));
            tvHoTen2.setText(bundle.getString("hoTen"));
            tvDiaChi2.setText(bundle.getString("diaChi"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
            format.setTimeZone(TimeZone.getTimeZone("UTC+7"));
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            try {
                Date date = format.parse(bundle.getString("thoiGian"));
                long diff = now.getTime() - date.getTime();
                long giay = (diff / 1000);
                long phut = (diff / (1000 * 60));
                long gio = (diff / (1000 * 60 * 60));
                long ngay = (diff / (1000 * 60 * 60 * 24));
                if (ngay > 3) {
                    tvThoiGianChiTiet2.setText(format2.format(date));
                }
                if (ngay < 3) {
                    tvThoiGianChiTiet2.setText(ngay + " ngày trước");
                }
                if (gio < 24 && gio > 0) {
                    tvThoiGianChiTiet2.setText(gio + " giờ trước");
                }
                if (phut < 60 && phut > 0) {
                    tvThoiGianChiTiet2.setText(phut + " phút trước");
                }
                if (giay < 60 && giay > 0) {
                    tvThoiGianChiTiet2.setText("vừa xong");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tvSdt2.setText("Số điện thoại: " + bundle.getString("soDienThoai") + "");
        }else {
            idMatHangChiTiet = bundle.getString("idMatHang");
        }
    }
    private void guiTinNhan(String idLienHe, String noiDung) {
        String idNguoiDung = LocalDataManager.getIdNguoiDung();
        Call<DuLieuTraVe> call = ApiService.apiService.chat(idNguoiDung, idLienHe, noiDung, "");
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {

            }
            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

                Log.d("guiTinNhan", "Loi: " + t.getMessage());
            }
        });
    }
}