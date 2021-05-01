package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.AnhAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.DiaChiAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.BaiViet;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Tinh;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangBaiActivity extends AppCompatActivity {
    RecyclerView rvAnh;
    AnhAdapter anhAdapter;
    TextView btnThemAnh,tvTieuDe;
    MaterialButton btnDang;
    TextInputEditText edNoiDung;
    ImageView imgBack;


    List<Uri> listPath = new ArrayList<>();
    List<String> listAnh = new ArrayList<>();
    String idBaiViet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_bai);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);


        rvAnh = findViewById(R.id.rvAnh);
        edNoiDung = findViewById(R.id.edNoiDung);
        btnThemAnh = findViewById(R.id.btnThemAnh);
        btnDang = findViewById(R.id.btnDang);
        tvTieuDe = findViewById(R.id.tvTieuDe);


        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        anhAdapter = new AnhAdapter(getApplicationContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3,RecyclerView.VERTICAL,false );
        rvAnh.setLayoutManager(gridLayoutManager);
        rvAnh.setAdapter(anhAdapter);

        Intent i = getIntent();
        if(i.getStringExtra("chucNang").equals("Cập nhật")){
            idBaiViet = i.getStringExtra("idBaiViet");
            loadChiTiet(idBaiViet);
            tvTieuDe.setText("Chỉnh sửa bài viết");
            btnDang.setText("Cập nhật");
            Log.d("DangBaiActivity", "onCreate: Cập nhật bài viết");
        }else {
            Log.d("DangBaiActivity", "onCreate: Tạo bài viết mới");
        }

        btnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonAnh();

            }
        });

        btnDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upAnhLenServer();


            }
        });
        edNoiDung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() > 0 || listPath.size() > 0 || listAnh.size() >0){
                    btnDang.setEnabled(true);
                }else {
                    btnDang.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void upAnhLenServer() {

        String regex = "(http://|https://|www\\.)([^ '\"]*)";
        List<String> anhCu = new ArrayList<>();
        List<Uri> anhMoi = new ArrayList<>();
        listPath.clear();
        for (String uri : listAnh){
            if(!Pattern.compile(regex).matcher(uri).matches()){
                anhMoi.add(Uri.parse(uri));
            }else {
                anhCu.add(uri);
            }

        }
        Log.d("list", "upAnhLenServer: "+listAnh);
        Log.d("anhCu", "upAnhLenServer: "+anhCu);
        Log.d("anhMoi", "upAnhLenServer: "+anhMoi);

        if(listAnh.size() > 0){
            List<String> listURL = new ArrayList<>();
            if(anhMoi.size() == 0){
                capNhatBaiViet(listAnh);
            }else {
                for(int i = 0 ;i < anhMoi.size();i++){
                    MediaManager.get().upload(anhMoi.get(i))
                            .unsigned("anhBaiViet").callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {

                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            if(idBaiViet == null){
                                listURL.add(resultData.get("url").toString());
                                Log.d("--", "onSuccess: "+listURL);
                                if(listURL.size() == anhMoi.size()){
                                    dangBaiViet(listURL);
                                    Log.d("DangBaiActivity", "onSuccess: Đăng bài viết");
                                }
                            }
                            else {
                                listURL.add(resultData.get("url").toString());
                                Log.d("--", "onSuccess: "+anhCu);
                                if(listURL.size() == anhMoi.size()){

                                    listURL.addAll(anhCu);

                                }
                                capNhatBaiViet(listURL);
                                Log.d("DangBaiActivity", "onSuccess: Cập nhật bài viết");
                            }
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }
                    }).dispatch();

                }
            }

        }
        else {
            Log.d("DangBaiActivity", "upAnhLenServer: Không có ảnh");
            dangBaiViet(null);
        }

    }

    private void dangBaiViet(List<String> listURL) {
        BaiViet baiViet = new BaiViet(edNoiDung.getText().toString(),listURL);
        Call<BaiViet> call = ApiService.apiService.dangBai(LocalDataManager.getIdNguoiDung(),baiViet);
        call.enqueue(new Callback<BaiViet>() {
            @Override
            public void onResponse(Call<BaiViet> call, Response<BaiViet> response) {
                BaiViet baiViet = response.body();
                Log.d("dangBaiViet", "onResponse: "+baiViet);
                Toast.makeText(getApplicationContext(), "Đăng bài thành công", Toast.LENGTH_SHORT).show();
                finish();

            }
            @Override
            public void onFailure(Call<BaiViet> call, Throwable t) {
                Log.d("dangBaiViet", "onFailure: "+t.getMessage());
                Toast.makeText(DangBaiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void capNhatBaiViet(List<String> listURL) {
        BaiViet baiViet = new BaiViet(edNoiDung.getText().toString(),listURL);
        Call<DuLieuTraVe> call = ApiService.apiService.capNhatBaiViet(idBaiViet,baiViet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                Toast.makeText(DangBaiActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Toast.makeText(DangBaiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void chonAnh() {
        TedBottomPicker.with(DangBaiActivity.this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Xác nhận")
                .setEmptySelectionText("Không ảnh nào được chọn")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        if(uriList != null && !uriList.isEmpty()){
                            uriList.forEach(uri -> listAnh.add(uri.toString()));
                            anhAdapter.loadAnhCu(listAnh);
                            Log.d("chonAnh", "onImagesSelected: "+listAnh);

                        }
                    }
                });
    }

    private void loadChiTiet(String idBaiViet){
        Call<DuLieuTraVe> call = ApiService.apiService.xemChiTiet(idBaiViet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                edNoiDung.setText(response.body().getBaiViet().getNoiDung());
                if(response.body().getBaiViet().getLinkAnh() != null){
                    listAnh = response.body().getBaiViet().getLinkAnh();
                    anhAdapter.loadAnhCu(listAnh);
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadChiTiet", "onFailure: "+t.getMessage());
                Toast.makeText(DangBaiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
