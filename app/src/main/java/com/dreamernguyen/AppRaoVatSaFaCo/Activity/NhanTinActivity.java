package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.TinNhanAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.MainActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.BaiViet;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TinNhan;
import com.dreamernguyen.AppRaoVatSaFaCo.MyService;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhanTinActivity extends AppCompatActivity {
    private EditText edTinNhan;
    private TextView tvTenNguoiDung;
    private Button btnGui;
    private ImageView btnUpload, imgBack;
    private static RecyclerView rvChat;
    private static TinNhanAdapter tinNhanAdapter;
    public static List<TinNhan> listTinNhan = new ArrayList<>();
    public String idNguoiDung, tenNguoiDung;
    public String intentActivity;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_tin);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.SkyBlue));
        }
        Intent i = getIntent();
        mRunnable.run();



        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDung);
        edTinNhan = findViewById(R.id.edTinNhan);
        btnGui = findViewById(R.id.btnGui);
        imgBack = findViewById(R.id.imgBack);
        btnUpload = findViewById(R.id.btnUpload);
        if (i.getExtras() != null) {
            intentActivity = i.getStringExtra("activity");
            idNguoiDung = i.getStringExtra("idNguoiDung");
            tenNguoiDung = i.getStringExtra("tenNguoiDung");
            loadTinNhan(idNguoiDung);
            if(intentActivity.equals("MatHangChiTiet")){
                if(i.getStringExtra("noiDung") != null){
                    guiTinNhan(idNguoiDung,i.getStringExtra("noiDung"));
                }
            }
        }
        tvTenNguoiDung.setText(tenNguoiDung);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guiAnh(idNguoiDung);
            }
        });
        rvChat = findViewById(R.id.rvChat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvChat.setLayoutManager(linearLayoutManager);

        tinNhanAdapter = new TinNhanAdapter(getApplicationContext());
        rvChat.setAdapter(tinNhanAdapter);
        edTinNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKeyboard();
            }
        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTinNhan.getText().toString().trim().length() > 0) {
                    guiTinNhan(idNguoiDung, edTinNhan.getText().toString());
                } else {
                    Toast.makeText(NhanTinActivity.this, "Không để trống tin nhắn !", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void checkTinNhanMoi(){
        if(MyService.listTinNhanMoi.size() > 0){
            String id = MyService.listTinNhanMoi.get(0).getIdNguoiGui().getId();
            if (id.equals(idNguoiDung)){
                loadTinNhan(id);
            }
        }
    }

    private void checkKeyboard() {
        final View activityRootView = findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(rect);

                int heightDiff = activityRootView.getRootView().getHeight() - rect.height();
                if ((heightDiff > 0.25 * activityRootView.getRootView().getHeight())) {
                    if (listTinNhan.size() > 0) {
                        rvChat.scrollToPosition(listTinNhan.size() - 1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    public  void loadTinNhan(String idLienHe) {
        Call<List<TinNhan>> call = ApiService.apiService.danhSachTinNhan(idLienHe, LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<List<TinNhan>>() {
            @Override
            public void onResponse(Call<List<TinNhan>> call, Response<List<TinNhan>> response) {
                listTinNhan = response.body();
                if (listTinNhan.size() > 0) {
                    MyService.listTinNhanMoi.clear();
                    List<TinNhan> listHienThi = new ArrayList<>();
                    for (int i = 0; i < listTinNhan.size(); i++) {
                        if (!listTinNhan.get(i).getXoaTinVoi().contains(LocalDataManager.getIdNguoiDung())) {
                            listHienThi.add(listTinNhan.get(i));
                        }
                    }
                    tinNhanAdapter.setData(listHienThi);
                    rvChat.scrollToPosition(listHienThi.size() - 1);
                }
            }
            @Override
            public void onFailure(Call<List<TinNhan>> call, Throwable t) {
                Log.d("loadTinNhan", "onFailure: " + t.getMessage());
            }
        });
    }

    private void guiTinNhan(String idLienHe, String noiDung) {
        edTinNhan.setText("");
        String idNguoiDung = LocalDataManager.getIdNguoiDung();
        Call<DuLieuTraVe> call = ApiService.apiService.chat(idNguoiDung, idLienHe, noiDung, "");
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                loadTinNhan(idLienHe);
            }
            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

                Log.d("guiTinNhan", "Loi: " + t.getMessage());
            }
        });
    }

    private void guiAnh(String idLienHe) {
        TedBottomPicker.with(this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Xác nhận")
                .setEmptySelectionText("Không ảnh nào được chọn")
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        MediaManager.get().upload(uri)
                                .unsigned("gybczcnv").callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                Log.d("trave", "onSuccess: " + resultData.get("url"));
                                String linkAnh = resultData.get("url").toString();
                                Call<DuLieuTraVe> call = ApiService.apiService.chat(LocalDataManager.getIdNguoiDung(), idLienHe, "", linkAnh);
                                call.enqueue(new Callback<DuLieuTraVe>() {
                                    @Override
                                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                        loadTinNhan(idLienHe);
                                    }
                                    @Override
                                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                        Log.d("loi", "onFailure: " + t.getMessage());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        handler.removeCallbacks(mRunnable);
        if (intentActivity.equals("ThongBao")) {
            Intent intent = new Intent(NhanTinActivity.this, MainActivity.class);
            intent.putExtra("chucNang", "TinNhanBack");
            startActivity(intent);
            finish();
        } else {
            finish();
        }

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            checkTinNhanMoi();
            handler.postDelayed(this,1000);

        }
    };
}
