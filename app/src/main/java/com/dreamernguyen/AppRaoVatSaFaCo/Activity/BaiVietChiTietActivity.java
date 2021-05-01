package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.AnhBaiVietAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.BinhLuanAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.NguoiDungAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.BaiViet;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.BinhLuan;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.like.LikeButton;
import com.like.OnLikeListener;

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

public class BaiVietChiTietActivity extends AppCompatActivity {
    TextView tvNoiDung,tvTenNguoiDung;
    List<String> listAnh = new ArrayList<>();
    AnhBaiVietAdapter anhBaiVietAdapter;
    BinhLuanAdapter binhLuanAdapter;
    ViewPager vpgAnh;
    String idBaiViet;
    RecyclerView rvBinhLuan;
    ImageView imTuyChinh;
    MaterialButton btnGui,btnLuotThich;
    TextView tvTim,tvTrangThai,tvThoiGian;
    LikeButton btnLike;
    EditText edBinhLuan;
    CircleImageView imgAvatar;
    LinearLayout layoutLoading,layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_viet_chi_tiet);
        tvNoiDung = findViewById(R.id.tvNoiDung);
        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDung);
        tvTrangThai = findViewById(R.id.tvTrangThai);
        tvThoiGian = findViewById(R.id.tvThoiGian);
        vpgAnh = findViewById(R.id.vpgImage);
        tvTim = findViewById(R.id.tvTim);
        btnLike = findViewById(R.id.btnLike);
        btnLuotThich = findViewById(R.id.btnLuotThich);
        imgAvatar = findViewById(R.id.imgAvatar);
        imTuyChinh = findViewById(R.id.imTuyChinh);
        layoutLoading = findViewById(R.id.layoutLoading);
        layoutContent = findViewById(R.id.layoutContent);
        binhLuanAdapter = new BinhLuanAdapter(this);
        rvBinhLuan = findViewById(R.id.rvBinhLuan);
        btnGui = findViewById(R.id.btnGui);
        edBinhLuan = findViewById(R.id.edBinhLuan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvBinhLuan.setLayoutManager(linearLayoutManager);
        Intent i = getIntent();
        if(i.getStringExtra("chucNang").equals("Xem")){
            idBaiViet = i.getStringExtra("idBaiViet");
            loadChiTiet(idBaiViet);
            loadBinhLuan(idBaiViet);
            btnGui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binhLuan(idBaiViet);
                }
            });
        }else {
            Toast.makeText(this, "Tạo bài mới", Toast.LENGTH_SHORT).show();
        }

    }
    private void loadChiTiet(String idBaiViet){
        Call<DuLieuTraVe> call = ApiService.apiService.xemChiTiet(idBaiViet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                layoutLoading.setVisibility(View.GONE);
                layoutContent.setVisibility(View.VISIBLE);
                BaiViet baiViet = response.body().getBaiViet();
                tvTenNguoiDung.setText(baiViet.getIdNguoiDung().getHoTen());
                Glide.with(getApplicationContext()).load(baiViet.getIdNguoiDung().getAvatar()).into(imgAvatar);
                tvTim.setText(baiViet.getLuotThich().size()+"");
                tvNoiDung.setText(baiViet.getNoiDung());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Calendar cal = Calendar.getInstance();
                Date now = cal.getTime();
                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                format.setTimeZone(TimeZone.getTimeZone("UTC+7"));

                try {
                    Date date = format.parse(baiViet.getThoiGianTao());
                    long diff = now.getTime() - date.getTime();
                    long giay = (diff / 1000);
                    long phut = (diff / (1000 * 60));
                    long gio = (diff / (1000 * 60 * 60));
                    long ngay = (diff / (1000 * 60 * 60 * 24));

                    if (ngay > 3) {
                        tvThoiGian.setText(format2.format(date));
                    }
                    if (ngay <= 3) {
                        tvThoiGian.setText(ngay + " ngày trước");
                    }
                    if (gio < 24 && gio > 0) {
                        tvThoiGian.setText(gio + " giờ trước");
                    }
                    if (phut < 60 && phut > 0) {
                        tvThoiGian.setText(phut + " phút trước");
                    }
                    if (giay < 60 && giay > 0) {
                        tvThoiGian.setText("Vừa xong");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (baiViet.getDaAn()) {
                    tvTrangThai.setText("Chỉ mình tôi");
                } else {
                    tvTrangThai.setText("Công khai");
                }
                if(baiViet.getLinkAnh().size() > 0){
                    listAnh = baiViet.getLinkAnh();
                    anhBaiVietAdapter = new AnhBaiVietAdapter(listAnh,true);
                    vpgAnh.setVisibility(View.VISIBLE);
                    vpgAnh.setAdapter(anhBaiVietAdapter);
                }else {
                    vpgAnh.setVisibility(View.GONE);
                }
                if(baiViet.getLuotThich().size() == 0){
                    btnLike.setLiked(false);
                }else {
                    for(int i = 0; i < baiViet.getLuotThich().size();i++){
                        if(baiViet.getLuotThich().get(i).getId().equals(LocalDataManager.getIdNguoiDung())){
                            btnLike.setLiked(true);
                        }
                    }
                }
                btnLike.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        Call<DuLieuTraVe> call = ApiService.apiService.thichBaiViet(idBaiViet,LocalDataManager.getIdNguoiDung());
                        call.enqueue(new Callback<DuLieuTraVe>() {
                            @Override
                            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                Log.d("Like", "onResponse: Đã like bài viết");
                                int soTim = Integer.parseInt(tvTim.getText().toString() )+ 1;
                                tvTim.setText(soTim+"");
                            }
                            @Override
                            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Lỗi like", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void unLiked(LikeButton likeButton) {
                        Call<DuLieuTraVe> call = ApiService.apiService.boThichBaiViet(idBaiViet,LocalDataManager.getIdNguoiDung());
                        call.enqueue(new Callback<DuLieuTraVe>() {
                            @Override
                            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                Log.d("Like", "onResponse: Đã like bài viết");
                                int soTim = Integer.parseInt(tvTim.getText().toString() ) - 1;
                                tvTim.setText(soTim+" Yêu thích");
                            }
                            @Override
                            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Lỗi dislike", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                imTuyChinh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BaiVietChiTietActivity.this,R.style.BottomSheetThemeCustom);
                        View viewDailog =   getLayoutInflater().inflate(R.layout.layout_bottom_sheet, new LinearLayout(BaiVietChiTietActivity.this));
                        bottomSheetDialog.setContentView(viewDailog);
                        bottomSheetDialog.show();
                        Dialog dialog = new Dialog(BaiVietChiTietActivity.this,R.style.BottomSheetThemeCustom);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_bao_cao_bai_viet);

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
                                    Call<DuLieuTraVe> call = ApiService.apiService.baoCao(baiViet.getId(),LocalDataManager.getIdNguoiDung(),a.get(0));
                                    call.enqueue(new Callback<DuLieuTraVe>() {
                                        @Override
                                        public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                            if(response.body().getThongBao() != null){
                                                Toast.makeText(getApplicationContext(), response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                            }
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }else {
                                    Toast.makeText(getApplicationContext(), "Vui lòng không chọn lí do báo cáo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        MaterialButton btnXoa,btnChinhSua,btnAn,btnAnKhoiToi,btnBaoCao,btnTheoDoi,btnBoTheoDoi;
                        btnXoa = viewDailog.findViewById(R.id.btnXoa);
                        btnChinhSua = viewDailog.findViewById(R.id.btnChinhSua);
                        btnAn = viewDailog.findViewById(R.id.btnAn);
                        btnAnKhoiToi = viewDailog.findViewById(R.id.btnAnKhoiToi);
                        btnBaoCao = viewDailog.findViewById(R.id.btnBaoCao);
                        btnTheoDoi = viewDailog.findViewById(R.id.btnTheoDoi);
                        btnBoTheoDoi = viewDailog.findViewById(R.id.btnBoTheoDoi);

                        if(baiViet.getIdNguoiDung().getDuocTheoDoi().size() > 0){
                            for(int i = 0; i < baiViet.getIdNguoiDung().getDuocTheoDoi().size();i++){
                                if(baiViet.getIdNguoiDung().getDuocTheoDoi().get(i).equals(LocalDataManager.getIdNguoiDung())){
                                    btnBoTheoDoi.setVisibility(View.VISIBLE);
                                    btnTheoDoi.setVisibility(View.GONE);

                                }else {
                                    btnBoTheoDoi.setVisibility(View.GONE);
                                    btnTheoDoi.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        if (baiViet.getIdNguoiDung().getId().equals(LocalDataManager.getIdNguoiDung())) {
                            btnXoa.setVisibility(View.VISIBLE);
                            btnChinhSua.setVisibility(View.VISIBLE);
                            btnAn.setVisibility(View.VISIBLE);
                            btnBaoCao.setVisibility(View.GONE);
                            btnAnKhoiToi.setVisibility(View.GONE);
                            btnBoTheoDoi.setVisibility(View.GONE);
                            btnTheoDoi.setVisibility(View.GONE);
                        } else {
                            btnXoa.setVisibility(View.GONE);
                            btnChinhSua.setVisibility(View.GONE);
                            btnAn.setVisibility(View.GONE);
                            btnBaoCao.setVisibility(View.VISIBLE);
                            btnAnKhoiToi.setVisibility(View.VISIBLE);
                        }
                        btnXoa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheetDialog.dismiss();
                                Call<DuLieuTraVe> call = ApiService.apiService.xoaBaiViet(baiViet.getId());
                                call.enqueue(new Callback<DuLieuTraVe>() {
                                    @Override
                                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                        Toast.makeText(getApplicationContext(), response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                        btnChinhSua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), DangBaiActivity.class);
                                intent.putExtra("chucNang","Cập nhật");
                                intent.putExtra("idBaiViet", baiViet.getId());
                                startActivity(intent);
                            }
                        });
                        btnAn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetDialog.dismiss();
                                Call<DuLieuTraVe> call = ApiService.apiService.anBaiViet(baiViet.getId());
                                call.enqueue(new Callback<DuLieuTraVe>() {
                                    @Override
                                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                        Toast.makeText(getApplicationContext(), response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetDialog.dismiss();
                                Call<DuLieuTraVe> call = ApiService.apiService.theoDoi(LocalDataManager.getIdNguoiDung(),baiViet.getIdNguoiDung().getId());
                                call.enqueue(new Callback<DuLieuTraVe>() {
                                    @Override
                                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                        Toast.makeText(getApplicationContext(), response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        btnAnKhoiToi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Call<DuLieuTraVe> call = ApiService.apiService.anBaiVietKhoiToi(baiViet.getId(),LocalDataManager.getIdNguoiDung());
                                call.enqueue(new Callback<DuLieuTraVe>() {
                                    @Override
                                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                        Toast.makeText(getApplicationContext(), response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        btnBaoCao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.show();
                            }
                        });
                    }
                });
                BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(BaiVietChiTietActivity.this,R.style.BottomSheetThemeCustom);
                View viewDialog2 =   getLayoutInflater().inflate(R.layout.dialog_luot_thich, new LinearLayout(BaiVietChiTietActivity.this));
                bottomSheetDialog2.setContentView(viewDialog2);

                RecyclerView rvLuotThich = viewDialog2.findViewById(R.id.rvLuotThich);
                TextView tv = viewDialog2.findViewById(R.id.tvThongBao);
                if (baiViet.getLuotThich().size() == 0){
                    tv.setVisibility(View.VISIBLE);
                    rvLuotThich.setVisibility(View.GONE);
                }else {
                    tv.setVisibility(View.GONE);
                    rvLuotThich.setVisibility(View.VISIBLE);
                }
                rvLuotThich.setLayoutManager(new LinearLayoutManager(BaiVietChiTietActivity.this,RecyclerView.VERTICAL,false));
                NguoiDungAdapter nguoiDungAdapter = new NguoiDungAdapter(BaiVietChiTietActivity.this);
                rvLuotThich.setAdapter(nguoiDungAdapter);
                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
                rvLuotThich.addItemDecoration(itemDecoration);
                nguoiDungAdapter.setData(baiViet.getLuotThich());
                btnLuotThich.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog2.show();
                    }
                });
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Toast.makeText(BaiVietChiTietActivity.this, "Lỗi" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadBinhLuan(String idBaiViet){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachBinhLuan(idBaiViet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                List<BinhLuan> listBinhLuan = response.body().getDanhSachBinhLuan();
                if(listBinhLuan.size() > 0){
                    binhLuanAdapter.setData(listBinhLuan);
                    rvBinhLuan.setAdapter(binhLuanAdapter);
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Toast.makeText(BaiVietChiTietActivity.this, "Lỗi" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void binhLuan(String idBaiViet){
        if(edBinhLuan.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập bình luận !", Toast.LENGTH_SHORT).show();
        }else {
            //ẩn bàn phím
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(edBinhLuan.getApplicationWindowToken(), 0);
            BinhLuan binhLuan = new BinhLuan(idBaiViet, edBinhLuan.getText().toString());
            Call<DuLieuTraVe> call = ApiService.apiService.binhLuan(LocalDataManager.getIdNguoiDung(), binhLuan);
            call.enqueue(new Callback<DuLieuTraVe>() {
                @Override
                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                    Toast.makeText(BaiVietChiTietActivity.this, "" + response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                    loadBinhLuan(idBaiViet);
                    edBinhLuan.setText("");
                }

                @Override
                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                    Toast.makeText(BaiVietChiTietActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
