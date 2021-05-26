package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangMatHangActivity extends AppCompatActivity {

    public static List<Uri> listURI = new ArrayList<>();
    public static List<String> listString = new ArrayList<>();
    public static List<String> listAnh = new ArrayList<>();

    public static String HangMuc, DanhMuc, DanhMucCon, tieuDe, noiDung, DiaChi, ThanhPho, QuanHuyen, PhuongXa;

    public static String idMatHangChiTiet;
    public static int giaBan, viTri = 0;
    public static String loadDiaChi ;
    public static String idDiaChi ;

    private FragmentRefreshListener fragmentRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_mat_hang);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
        Intent intent = getIntent();
        idMatHangChiTiet = intent.getStringExtra("idMatHangChiTiet");


        if (idMatHangChiTiet != null) {
            loadMatHangChiTiet();

            return;
        }
        if (DanhMuc != null && !DanhMuc.isEmpty()) {
            showHuyDialog();
        }
    }

    public void showHuyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DangMatHangActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(DangMatHangActivity.this).inflate(
                R.layout.dialog_dang_mat_hang,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.idtextTitle)).setText("Tiếp tục với mặt hàng đã có");
        ((TextView) view.findViewById(R.id.idtextMesage)).setText("Bạn có thể tiếp tục tin đang viết hoặc hủy bỏ tạo một tin mới");
        ((AppCompatButton) view.findViewById(R.id.btnButtonYes)).setText("Tiếp tục");
        ((AppCompatButton) view.findViewById(R.id.btnButtonNo)).setText("Làm mới");

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btnButtonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btnButtonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                clearAll();
                if (getFragmentRefreshListener() != null) {
                    getFragmentRefreshListener().onRefresh();
                }
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void loadMatHangChiTiet() {
        Call<DuLieuTraVe> call = ApiService.apiService.xemChiTietMatHang(idMatHangChiTiet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                MatHang matHang = response.body().getMatHang();
                giaBan = matHang.getGiaBan();
                String diaChi = matHang.getDiaChi();
                int a1 = diaChi.indexOf("-");
                int a2 = diaChi.lastIndexOf("-");

                PhuongXa = diaChi.substring(0, a1);
                QuanHuyen = diaChi.substring(a1 +1, a2);
                ThanhPho = diaChi.substring(a2 + 2);
                String hangMuc = matHang.getHangMuc();
                DanhMuc = hangMuc.substring(0, hangMuc.indexOf("-"));
                DanhMucCon = hangMuc.substring(hangMuc.indexOf("-")+1);
                tieuDe = matHang.getTieuDe();
                noiDung = matHang.getMoTa();
                listString = matHang.getLinkAnh();
                getFragmentRefreshListener().onRefresh();

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });
    }

    public static void clearAll() {
        listURI = new ArrayList<>();
        listAnh = new ArrayList<>();
        listString = new ArrayList<>();
        idMatHangChiTiet="";
        HangMuc = "";
        DanhMuc = "";
        DanhMucCon = "";
        tieuDe = "";
        noiDung = "";
        DiaChi = "";
        ThanhPho = "";
        QuanHuyen = "";
        PhuongXa = "";
        giaBan = 0;
        viTri = 0;
    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public interface FragmentRefreshListener {
        void onRefresh();
    }

}






