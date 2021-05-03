
package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.navigation.Navigation;

        import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
        import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
        import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
        import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
        import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
        import com.dreamernguyen.AppRaoVatSaFaCo.R;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;


public class TimKiemMatHangActivity extends AppCompatActivity {

    public static int viTri,idThanhPho,idQuan =0;

    public static TimKiem timKiemTemp;

    public static List<MatHang> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_mat_hang);


        Intent intent = getIntent();
        String HangMuc = intent.getStringExtra("hangMuc");

        layDuLieu();


        if (HangMuc != null && !HangMuc.isEmpty()) {
            createTemp("", HangMuc, "", "-1", "", "DangTao");
        } else {
            Navigation.findNavController(this,R.id.nav_host).navigate(R.id.action_timKiemMatHangKetQuaFragment_to_timKiemMatHangNhanhFragment);
            createTemp("", "", "", "-1", "", "DangTao");
        }
    }

    public void createTemp(String tieuDe, String danhMuc, String diaChi, String sapXepThoiGian, String sapXepGiaBan, String trangThai) {

        timKiemTemp = new TimKiem(tieuDe, danhMuc,  diaChi,sapXepThoiGian, sapXepGiaBan, trangThai);
        TimKiemDataBase.getInstance(getApplicationContext()).timKiemDAO().createTemp(timKiemTemp);
        timKiemTemp = TimKiemDataBase.getInstance(getApplicationContext()).timKiemDAO().findTemp("DangTao");
        Log.d("TAG", "createTemp: da tao " + timKiemTemp.getTrangThai()+" hang muc" +timKiemTemp.getDanhMuc()+" gia ban"+timKiemTemp.getSapXepGiaBan()+" thoi gian "+timKiemTemp.getSapXepThoiGian());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void layDuLieu(){

        Call<DuLieuTraVe> call = ApiService.apiService.danhSachMatHang();
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                list = response.body().getDanhSachMatHang();
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }

        });

    }

}